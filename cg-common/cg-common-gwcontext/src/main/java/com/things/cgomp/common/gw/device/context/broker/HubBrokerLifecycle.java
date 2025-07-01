package com.things.cgomp.common.gw.device.context.broker;


import com.things.cgomp.common.gw.device.context.broker.constant.State;
import com.things.cgomp.common.gw.device.context.session.SessionContext;

public interface HubBrokerLifecycle<P> extends IBroker<P> {


    /**
     * 获取broker的状态
     *
     * @return
     */
    State getState();

    SessionContext getSessionContext();


    /**
     * 销毁设备会话
     *
     * @param eventTime
     * @param deviceId
     */
    boolean destroySession(Long eventTime, Long deviceId);

    /**
     * 刷新设备会话
     *
     * @param eventTime
     * @param deviceId
     */
    boolean refreshSession(Long eventTime, Long deviceId);
}
