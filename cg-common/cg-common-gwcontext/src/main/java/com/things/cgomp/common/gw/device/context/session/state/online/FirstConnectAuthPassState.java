package com.things.cgomp.common.gw.device.context.session.state.online;

import com.things.cgomp.common.gw.device.context.api.DeviceLifecycleMessageService;
import com.things.cgomp.common.gw.device.context.broker.config.HubBrokerConfig;
import com.things.cgomp.common.gw.device.context.broker.config.ServiceNodeInfo;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.mq.message.DeviceOnLineReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FirstConnectAuthPassState extends BaseConnectState {
    public FirstConnectAuthPassState(SessionContext sessionContext, DeviceLifecycleMessageService deviceLifecycleMessageService) {
        super(sessionContext, deviceLifecycleMessageService);
    }

    @Override
    boolean doBusinessExe(Session session) {
        if (session.isAuth()) {
            HubBrokerConfig brokerConfig = sessionContext.getBroker()
                    .getBrokerConfig();
            ServiceNodeInfo hubNodeInfo = brokerConfig
                    .getHubNodeInfo();

            Long validTime = sessionContext.getBroker().getValidTime(session);
            DeviceOnLineReqMsg reqMsg = DeviceOnLineReqMsg.builder()
                    .onLineTime(System.currentTimeMillis())
                    .nodeId(hubNodeInfo.getNodeId())
                    .validTime(validTime)
                    .brokerId(brokerConfig.getBrokerId())
                    .deviceId(session.getDeviceId())
                    .build();
            deviceLifecycleMessageService.onLine(reqMsg, session, new SimpleServiceCallback<Void>() {
                @Override
                public void onSuccess(Void msg) {
                    log.info("发送上线消息成功,reqMsg={}", reqMsg);
                }

                @Override
                public void onError(Throwable e) {
                    log.error("发送上线消息失败,reqMsg={}", reqMsg, e);

                }
            });
        } else {
            log.error("不能上线,会话认证标识为false,session={}", session);

        }
        return true;
    }

    @Override
    public String toString() {
        return "FirstConnectState [inactive=" + "认证通过" + "]";
    }
}
