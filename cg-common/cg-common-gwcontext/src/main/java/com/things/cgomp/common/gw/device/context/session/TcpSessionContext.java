package com.things.cgomp.common.gw.device.context.session;

import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.common.gw.device.context.broker.IBroker;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;


public class TcpSessionContext extends BaseSessionContext<TcpSession, Channel> {

    public static AttributeKey<Session> S_DEVICE_SESSION = AttributeKey
            .valueOf("device.session");

    public static final AttributeKey<Boolean> S_DEVICE_RELEASE = AttributeKey
            .valueOf("device.reLease");

    public TcpSessionContext(IDeviceServiceApi deviceService, IBroker broker) {
        super(deviceService,broker);
    }

    @Override
    public TcpSession createSession(
            Channel channel,
            String sessionId,
            String clientIp,
            Long deviceId,
            Long productId,
            String sn,
            String deviceConfig
    ) {
        Integer abnormalOffLineTime = getAbnormalOffLineTime();
        TcpSession session = new TcpSession();
        session.setAuth(true);
        session.setDeviceId(deviceId);
        session.setProductId(productId);
        session.setStartTime(System.currentTimeMillis());
        session.setSn(sn);
        session.setChannel(channel);
        session.setClientIp(clientIp);
        session.setSessionId(sessionId);
        session.setStartTime(System.currentTimeMillis());
        session.setAbnormalOffLineTime(abnormalOffLineTime);
        channel.attr(S_DEVICE_SESSION).set(session);
        return session;
    }


}
