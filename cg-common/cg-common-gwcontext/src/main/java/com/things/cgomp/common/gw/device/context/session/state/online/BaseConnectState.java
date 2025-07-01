package com.things.cgomp.common.gw.device.context.session.state.online;

import com.things.cgomp.common.gw.device.context.api.DeviceLifecycleMessageService;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class BaseConnectState implements IOnLine {

    protected SessionContext sessionContext;
    protected DeviceLifecycleMessageService deviceLifecycleMessageService;

    public BaseConnectState(SessionContext sessionContext, DeviceLifecycleMessageService deviceLifecycleMessageService) {
        this.sessionContext = sessionContext;
        this.deviceLifecycleMessageService = deviceLifecycleMessageService;
    }


    @Override
    public void exe(Session session) {
        try {
            doBusinessExe(session);
        } catch (Exception e) {
            log.error("失败:" + this.toString() + ",session=" + session, e);

        }


    }


    abstract boolean doBusinessExe(Session session);


}
