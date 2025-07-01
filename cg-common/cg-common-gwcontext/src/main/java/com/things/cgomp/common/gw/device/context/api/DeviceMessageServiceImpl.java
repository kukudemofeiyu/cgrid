package com.things.cgomp.common.gw.device.context.api;

import com.alibaba.fastjson2.JSON;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.dao.device.mapper.DeviceInfoMapper;
import com.things.cgomp.common.device.pojo.queue.AuthReqMsg;
import com.things.cgomp.common.device.pojo.queue.AuthResponseMsg;
import com.things.cgomp.common.gw.device.context.api.queue.MessageRequestTemplate;
import com.things.cgomp.common.gw.device.context.broker.IBroker;
import com.things.cgomp.common.gw.device.context.broker.config.HubBrokerConfig;
import com.things.cgomp.common.gw.device.context.broker.config.ServiceNodeInfo;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.gw.device.context.utils.AsyncCallbackTemplate;
import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.*;
import com.things.cgomp.common.mq.message.callback.CallBackMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class DeviceMessageServiceImpl implements DeviceMessageService {

    @Autowired
    private DeviceInfoMapper deviceDao;

    @Autowired
    private ServiceNodeInfo serviceNodeInfo;

    private ExecutorService callbackExecutor;
    private ScheduledExecutorService timeoutExecutor;

    @Autowired
    private MessageRequestTemplate deviceMessageDispatchTemplate;

    @PostConstruct
    public void init() {
        this.callbackExecutor = Executors.newWorkStealingPool(20);
        this.timeoutExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void auth(AuthReqMsg reqMsg, SimpleServiceCallback<CallBackMsg<AuthResponseMsg>> callback) {
        log.info("auth:{}", reqMsg);
        AsyncCallbackTemplate.withCallbackAndTimeout(getDeviceInfo(reqMsg), device -> {
            CallBackMsg callBackMsg = CallBackMsg.builder().build();
            if (device == null) {
                callBackMsg.setCode(1);
                callBackMsg.setErrorMsg("设备信息不存在");
            } else {
                AuthResponseMsg msg = AuthResponseMsg.builder()
                        .sn(device.getSn())
                        .deviceId(device.getDeviceId())
                        .aliasSn(device.getAliasSn())
                        .build();
                callBackMsg.setMsg(msg);
            }
            callback.onSuccess(callBackMsg);


        }, callback::onError, 10000, timeoutExecutor, callbackExecutor);
    }

    @Override
    public void onLine(DeviceOnLineReqMsg reqMsg, Session session, SimpleServiceCallback<Void> callback) {

        String txId = UUID.randomUUID().toString().replaceAll("-", "");
        String reqId = UUID.randomUUID().toString().replaceAll("-", "");
        QueueMsg queueMsg = QueueMsg.builder()
                .metadata(Metadata.builder().deviceId(session.getDeviceId())
                        .eventTime(System.currentTimeMillis())
                        .txId(txId)
                        .serviceInfo(getNodeInfo())
                        .requestId(reqId)
                        .build())
                        .body(reqMsg).build();
        deviceMessageDispatchTemplate.asyncSend(MQTopics.STATUS, MQTopics.Tag.ONLINE, queueMsg, callback);
    }

    @Override
    public void offLine(DeviceOffLineReqMsg reqMsg, Session session, SimpleServiceCallback<Void> callback) {
        offLine(reqMsg, session.getDeviceId(), callback);

        if (null != session.getChildDevices()) {
            Map<String /*设备的deviceName */, SessionDeviceInfo> map = session.getChildDevices();
            map.forEach((k, v) ->
            {
                offLine(reqMsg, v.getDeviceId(), new SimpleServiceCallback<Void>() {
                    @Override
                    public void onSuccess(Void msg) {
                        log.debug("充电枪下线成功, sn={}", k);
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.error("充电枪下线成功失败, sn={}", v.getSn());
                    }
                });
            });

        }
    }


    private void offLine(DeviceOffLineReqMsg reqMsg, Long deviceId, SimpleServiceCallback<Void> callback) {
        String txId = UUID.randomUUID().toString().replaceAll("-", "");
        String reqId = UUID.randomUUID().toString().replaceAll("-", "");
        QueueMsg queueMsg = QueueMsg.builder()
                .metadata(Metadata.builder().deviceId(deviceId)
                        .eventTime(System.currentTimeMillis())
                        .txId(txId)
                        .serviceInfo(getNodeInfo())
                        .requestId(reqId)
                        .build())
                .body(reqMsg).build();
        deviceMessageDispatchTemplate.asyncSend(MQTopics.STATUS, MQTopics.Tag.OFFLINE, queueMsg, callback);
    }

    @Override
    public void syncSessionInfo(DeviceSessionSyncReqMsg reqMsg, Session session, SimpleServiceCallback<Void> callback) {
        String txId = UUID.randomUUID().toString().replaceAll("-", "");
        String reqId = UUID.randomUUID().toString().replaceAll("-", "");
        QueueMsg queueMsg = QueueMsg.builder()
                .metadata(Metadata.builder().deviceId(session.getDeviceId())
                        .eventTime(System.currentTimeMillis())
                        .serviceInfo(getNodeInfo())
                        .txId(txId)
                        .requestId(reqId)
                        .build())
                .body(reqMsg).build();
        deviceMessageDispatchTemplate.asyncSend(MQTopics.STATUS, MQTopics.Tag.SESSION_SYNC, queueMsg, callback);
    }

    private ServiceInfo getNodeInfo(){
       return ServiceInfo.builder()
                .nodeId(serviceNodeInfo.getNodeId()).serviceName(serviceNodeInfo.getServiceName())
               .topic(serviceNodeInfo.getTopic())
                .build();
    }


    private ListenableFuture<DeviceInfo> getDeviceInfo(AuthReqMsg reqMsg) {
        try {
            ListeningExecutorService executorService = MoreExecutors.listeningDecorator(callbackExecutor);
            return executorService.submit(() ->
                    {
                        String authParamJson = JSON.toJSONString(reqMsg.getAuthParam());
                        DeviceInfo deviceInfoDTO = JSON.parseObject(authParamJson, DeviceInfo.class);
                        return deviceDao.findDeviceInfo(deviceInfoDTO);
                    }
            );
        } catch (Exception e) {
            log.error("queryDevice.error", e);
            return null;
        }
    }


    @Override
    public void sendData(String topic, String tag, AbstractBody deviceDataReqMsg, Session session, Long childId,Integer messageType, Long eventTs, SimpleServiceCallback<Void> callback) {
        sendData(topic, tag, deviceDataReqMsg, session.getDeviceId(), childId, messageType, eventTs, callback);
    }

    public void sendData(String topic, String tag, AbstractBody deviceDataReqMsg, Long deviceId, Long childId, Integer messageType, Long eventTs, SimpleServiceCallback<Void> callback) {
        String txId = UUID.randomUUID().toString().replaceAll("-", "");
        String reqId = UUID.randomUUID().toString().replaceAll("-", "");
        QueueMsg queueMsg = QueueMsg.builder()
                .metadata(Metadata.builder().deviceId(deviceId)
                        .portId(childId)
                        .payloadCode(messageType)
                        .eventTime(eventTs)
                        .serviceInfo(getNodeInfo())
                        .txId(txId)
                        .requestId(reqId)
                        .build())
                .body(deviceDataReqMsg).build();
        deviceMessageDispatchTemplate.asyncSend(topic, tag, queueMsg, callback);
    }

    @Override
    public void sendCmdLogData(DeviceCmdLogReqMsg deviceDataReqMsg, Session session, String  gunNo  , Integer messageType, Long ts) {
        String txId = UUID.randomUUID().toString().replaceAll("-", "");
        String reqId = UUID.randomUUID().toString().replaceAll("-", "");
        QueueMsg queueMsg = QueueMsg.builder()
                .metadata(Metadata.builder().deviceId(session.getDeviceId())
                        .payloadCode(messageType)
                        .eventTime(ts == null ? System.currentTimeMillis(): ts )
                        .serviceInfo(getNodeInfo())
                        .txId(txId)
                        .requestId(reqId)
                        .portId(getPortId(session, gunNo))
                        .build())
                .body(deviceDataReqMsg).build();
        deviceMessageDispatchTemplate.asyncSend(MQTopics.CMD_lOG, null, queueMsg, new SimpleServiceCallback<CallBackMsg>() {
            @Override
            public void onSuccess(CallBackMsg msg) {
//                log.debug("发送设备交互日志成功, msg={}",deviceDataReqMsg );
            }

            @Override
            public void onError(Throwable e) {
                log.error("发送设备交互日志失败, txId={}, reqId={} ", txId, reqId, e);
            }
        });
    }

    @Override
    public Boolean sendOrderRecordTransactionMsg(String topic, String tag, AbstractBody deviceDataReqMsg, Session session, Long childId, Integer messageType, Long eventTs) {
        String txId = UUID.randomUUID().toString().replaceAll("-", "");
        String reqId = UUID.randomUUID().toString().replaceAll("-", "");
        QueueMsg queueMsg = QueueMsg.builder()
                .metadata(Metadata.builder().deviceId(session.getDeviceId())
                        .payloadCode(messageType)
                        .eventTime(eventTs)
                        .serviceInfo(getNodeInfo())
                        .txId(txId)
                        .requestId(reqId)
                        .portId(childId)
                        .build())
                .body(deviceDataReqMsg).build();
        return deviceMessageDispatchTemplate.sendTransactionMsg(topic, tag, queueMsg, txId);

    }

    private Long getPortId(Session session, String gunNo) {
        Long portId = null;
        if(gunNo !=null){
            SessionDeviceInfo childDeviceInfo = session.getChildDeviceInfo(gunNo);
            if(childDeviceInfo != null){
                portId = childDeviceInfo.getDeviceId();
            }
        }
        return portId;
    }

    public void authChild(SessionContext sessionContext, Session session, String deviceName) {
        IBroker broker = sessionContext.getBroker();
        HubBrokerConfig brokerConfig = broker.getBrokerConfig();
        ServiceNodeInfo hubNodeInfo = brokerConfig.getHubNodeInfo();
        Map<String, Object> authParam = new HashMap<>();
        authParam.put("aliasSn", deviceName);
        authParam.put("parentId",session.getDeviceId());
        auth(AuthReqMsg.builder().authParam(authParam).build(),new SimpleServiceCallback<CallBackMsg<AuthResponseMsg>>() {
            @Override
            public void onSuccess(CallBackMsg<AuthResponseMsg> msg) {
                Integer recode = msg.getCode();
                if (recode.intValue() == 0) {
                    AuthResponseMsg bodyMsg = msg.getMsg();
                    SessionDeviceInfo deviceInfo = SessionDeviceInfo.builder().deviceId(bodyMsg.getDeviceId()).sn(bodyMsg.getAliasSn())
                            .qrSetRetryTimes(new AtomicInteger(0))
                            .qrSetResult(false)
                            .productId(bodyMsg.getProductId()).build();
                    session.addChildDeviceInfo(deviceName, deviceInfo);
                    // 上线
                    Long validTime = sessionContext.getBroker().getValidTime(session);
                    DeviceOnLineReqMsg reqMsg = DeviceOnLineReqMsg.builder()
                            .onLineTime(System.currentTimeMillis())
                            .nodeId(hubNodeInfo.getNodeId())
                            .validTime(validTime)
                            .brokerId(brokerConfig.getBrokerId())
                            .deviceId(deviceInfo.getDeviceId())
                            .build();
                    onLine(reqMsg, session, new SimpleServiceCallback<Void>() {
                        @Override
                        public void onSuccess(Void msg) {
                            log.info("发送充电枪上线消息成功,deviceId={}, sn={}, reqMsg={}",deviceInfo.getDeviceId(),deviceInfo.getSn(), reqMsg);
                        }

                        @Override
                        public void onError(Throwable e) {
                            log.error("发送充电枪上线消息失败,deviceId={}, sn={},reqMsg={}", deviceInfo.getDeviceId(),deviceInfo.getSn(),reqMsg, e);
                        }
                    });

                } else {
                    log.error("充电枪认证失败,authParam={}, message={}", authParam, msg.getErrorMsg());
                }


            }

            @Override
            public void onError(Throwable e) {
                log.error("充电枪认证异常 ,deviceName={}", deviceName, e);
            }
        });

    }
}
