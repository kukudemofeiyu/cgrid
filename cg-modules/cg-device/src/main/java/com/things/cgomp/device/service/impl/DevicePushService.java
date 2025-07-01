package com.things.cgomp.device.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.alibaba.nacos.shaded.com.google.gson.GsonBuilder;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.dao.device.mapper.DeviceInfoMapper;
import com.things.cgomp.common.device.dao.node.domain.ServiceInfo;
import com.things.cgomp.common.device.dao.node.mapper.BrokerServiceRunInfoDao;
import com.things.cgomp.common.device.dao.node.mapper.ServiceInfoDao;
import com.things.cgomp.common.device.pojo.device.CommandBaseConfig;
import com.things.cgomp.common.device.pojo.device.DeviceCommandEnum;
import com.things.cgomp.common.device.pojo.device.DeviceConnectDO;
import com.things.cgomp.common.device.pojo.device.push.ResponseData;
import com.things.cgomp.device.enums.ErrorCodeConstants;
import com.things.cgomp.device.enums.HubHttpServiceUri;
import com.things.cgomp.device.service.IDeviceInfoService;
import com.things.cgomp.device.service.IDevicePushService;
import com.things.cgomp.common.device.pojo.device.push.PushInfo;
import com.things.cgomp.common.device.pojo.device.push.PushResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class DevicePushService implements IDevicePushService {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;
    @Autowired
    private BrokerServiceRunInfoDao brokerServiceRunInfoDao;
    @Autowired
    private ServiceInfoDao serviceInfoDao;

    private final static CloseableHttpClient HTTP_CLIENT = HttpClientBuilder.create()
            .setMaxConnTotal(200)
            .setMaxConnPerRoute(50)
            .build();

    @Override
    public PushResult push(Long gridId, Long  portId, DeviceCommandEnum pushMessageType, Object context) throws Exception{
        return push(gridId, portId,  pushMessageType, context, null, HubHttpServiceUri.PUSH);
    }

    private PushResult push(Long gridId, Long portId, DeviceCommandEnum pushMessageType, Object context, PushInfo pushInfo, String url) throws Exception{
        try {

            Map<String, Object> pushRequestMap = new HashMap<>(8);
            if (Objects.nonNull(pushInfo)) {
                Map<String, Object> pushInfoMap = JSON.parseObject(JSON.toJSONString(pushInfo), pushRequestMap.getClass());
                pushRequestMap.putAll(pushInfoMap);
            }

            //组装下发的枪信息
            if(portId != null){
                DeviceInfo deviceInfo = deviceInfoMapper.selectPortDevice(portId);
                gridId = deviceInfo.getParentId();
                pushRequestMap.put("portId", portId);
                pushRequestMap.put("gunNo", deviceInfo.getAliasSn());
            }

            //组装下发的参数
            DeviceConnectDO deviceConnectDO = deviceInfoMapper.selectDeviceConnnectInfo(gridId);
            if (null == deviceConnectDO) {
                throw new ServiceException(ErrorCodeConstants.DEVICE_NOT_EXIST);
            }
            String transactionId = UUID.randomUUID().toString();
            pushRequestMap.put("transactionId", transactionId);
            pushRequestMap.put("deviceNo", deviceConnectDO.getSn());
            pushRequestMap.put("connectId", deviceConnectDO.getDeviceId());
            pushRequestMap.put("context", JSONObject.parseObject(JSONObject.toJSONString(context)));
            pushRequestMap.put("deviceCommand", pushMessageType);

            // 封装路由broker信息
            buildRouteInfo(pushRequestMap, deviceConnectDO, pushMessageType, url);

            HttpPost httpPost = buildHttpPost(String.valueOf(pushRequestMap.get("uri")), pushRequestMap);
            CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost);
            String entityStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            ResponseData<Object> responseData =  JSON.parseObject(
                    entityStr,
                    new TypeReference<ResponseData<Object>>() {
                    });
            log.info("下发指令回复:  pushInfo={}, code={}, message={}, data={}", pushInfo,  responseData.getCode(), responseData.getMsg(), JSON.toJSON(responseData.getData()));

            if (responseData.getCode() == 200) {
                return new GsonBuilder().create()
                        .fromJson(JSON.toJSONString(responseData.getData()), PushResult.class);
            }else{
                return PushResult.builder()
                        .succeed(false)
                        .codeMsg(responseData.getMsg())
                        .build();
            }

        } catch (Exception e) {
            log.error("操作失败: pushInfo={},,error={}", pushInfo, e.getMessage(), e);
            String errorMsg = getErrorMsg(e);
            return PushResult.builder()
                    .succeed(false)
                    .codeMsg(errorMsg)
                    .build();

        }
    }

    private String getErrorMsg(Exception e) {
        if (e instanceof ServiceException) {
            return  ((ServiceException) e).getMessage();
        } else {
            return "操作失败";
        }
    }

    private void buildRouteInfo(Map<String, Object> pushRequestMap, DeviceConnectDO deviceConnectDO,DeviceCommandEnum commandEnum, String url) {
        ServiceInfo serviceInfo = serviceInfoDao.findServiceInfoByServiceId(deviceConnectDO.getNodeId());
        if(serviceInfo == null ){
            throw new ServiceException(ErrorCodeConstants.NO_PUSH_CHANNEL_BE_AVAILABLE);
        }

        List<String> activeServiceId = brokerServiceRunInfoDao.findLastActiveServiceIdByBrokerId(deviceConnectDO.getBrokerId());
        if(CollectionUtils.isEmpty(activeServiceId)){
            throw new ServiceException(ErrorCodeConstants.NO_PUSH_CHANNEL_BE_AVAILABLE);
        }
        boolean contains = activeServiceId.contains(serviceInfo.getServiceId());
        if(!contains){
            throw new ServiceException(ErrorCodeConstants.NO_PUSH_CHANNEL_BE_AVAILABLE);
        }

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder urlBuilder = stringBuilder.append("http://").append(serviceInfo.getVisitIp())
                .append(":").append(serviceInfo.getVisitPort()).append(serviceInfo.getContextPath())
                .append(url);
        pushRequestMap.put("serviceId",serviceInfo.getServiceId());
        pushRequestMap.put("brokerId", deviceConnectDO.getBrokerId());
        pushRequestMap.put("uri",urlBuilder.toString());

    }

    private HttpPost buildHttpPost(
            String uri,
            Map<String, Object> pushRequestMap
    ) {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        String content = new Gson().toJson(pushRequestMap);
        StringEntity stringEntity = new StringEntity(content, "UTF-8");
        stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(stringEntity);
        return httpPost;
    }

}
