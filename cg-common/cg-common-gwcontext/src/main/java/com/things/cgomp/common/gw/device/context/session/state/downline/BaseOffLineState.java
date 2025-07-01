package com.things.cgomp.common.gw.device.context.session.state.downline;

import com.things.cgomp.common.gw.device.context.api.DeviceMessageService;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionCloseCause;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.mq.message.DeviceOffLineReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class BaseOffLineState implements IOffLine {

    protected SessionContext sessionContext;
    protected SessionCloseCause closeCause;
    private DeviceMessageService deviceMessageService;

    public BaseOffLineState(SessionContext sessionContext, SessionCloseCause closeCause, DeviceMessageService deviceMessageService) {
        this.sessionContext = sessionContext;
        this.closeCause = closeCause;
        this.deviceMessageService = deviceMessageService;
    }

    @Override
    public void exe(Session session) {
        DeviceOffLineReqMsg reqMsg = DeviceOffLineReqMsg.builder()
                .sessionStartTime(session.getStartTime())
                .sessionCountTime(System.currentTimeMillis() - session.getStartTime())
                .causeType(closeCause.getType())
                .cause(closeCause.getDesc())
                .build();
        deviceMessageService.offLine(reqMsg, session, new SimpleServiceCallback<Void>() {
            @Override
            public void onSuccess(Void msg) {
                log.debug("disConnect success message,session={}", session);
            }

            @Override
            public void onError(Throwable e) {
                log.error("disConnect error message,session={}", session);
            }
        });

    }
}
