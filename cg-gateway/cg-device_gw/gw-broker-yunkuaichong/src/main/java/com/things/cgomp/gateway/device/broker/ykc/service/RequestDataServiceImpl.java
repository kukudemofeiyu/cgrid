package com.things.cgomp.gateway.device.broker.ykc.service;

import com.alibaba.fastjson2.JSON;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.device.api.RemoteDeviceService;
import com.things.cgomp.pay.api.RemoteRuleService;
import com.things.cgomp.device.api.dto.RuleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants.REQUEST_PAY_RULE_FAIL;

@Slf4j
@Component
public class RequestDataServiceImpl implements IRequestDataService{

    @Autowired
    private IDeviceServiceApi deviceServiceApi;
    @Autowired
    private RemoteRuleService remoteRuleService;

    @Value("${ykc.auth.privateKey}")
    private String privateKey;

    @Value("${ykc.auth.publicKey}")
    private String publicKey;

    @Autowired
    private RemoteDeviceService remoteDeviceService;

    public RuleDTO getPayRule(Long deviceId) throws Exception{
        DeviceInfo deviceInfo = deviceServiceApi.getDeviceInfoById(deviceId);
        R<RuleDTO> ruleDTOR = remoteRuleService.selectRule(deviceInfo.getPayRuleId());
        if (ruleDTOR == null || !R.isSuccess(ruleDTOR) || ruleDTOR.getData() == null) {
            throw new ServiceException(REQUEST_PAY_RULE_FAIL);
        }

        log.info("获取计费规则成功: deviceNo:{}, payRule:{}", deviceInfo.getSn(), JSON.toJSONString(ruleDTOR.getData()));
        return ruleDTOR.getData();
    }


    public Map<String,String> getRSAKey(Long deviceId) throws Exception{
        return new HashMap<String, String>(){{
            put("publicKey", publicKey);
            put("privateKey", privateKey);
        }};
    }

    @Override
    public String getOrderNo(Long deviceId, LocalDateTime insertGunTime, String vin) throws Exception{

        long epochMilli = insertGunTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        R<String> stringR = remoteDeviceService.insertGun(deviceId, epochMilli,vin);
        if(stringR != null && R.isSuccess(stringR)  && !StringUtils.isEmpty(stringR.getData()) ) {
            return stringR.getData();
        }

        return null;


    }


}
