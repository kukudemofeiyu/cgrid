package com.things.cgomp.common.gw.device.context.session;


import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.common.gw.device.context.broker.IBroker;

public interface SessionContext<Key, T extends Session, channel> {

    /**
     * 创建一个会话
     *
     * @param channel
     * @return
     */
    T createSession(
            channel channel,
            String sessionId,
            String clientIp,
            Long deviceId,
            Long productId,
            String sn,
            String deviceConfig
    );

    /**
     * 保持会话
     *
     * @param session
     * @return
     */
    T keepSession(T session);

    /**
     * 释放会话
     *
     * @param session
     * @param closeCause
     * @param
     */
    void releaseSession(T session, SessionCloseCause closeCause, boolean isDelSession);

    /**
     * 获取会话
     *
     * @param key
     * @return
     */
    T getSession(Key key);

    void putSession(Key deviceId, T session);

    /**
     * 获取会话通道
     *
     * @param key
     * @return
     */
    channel getChannel(Key key);

    IDeviceServiceApi getDeviceService();

    IBroker getBroker();





}
