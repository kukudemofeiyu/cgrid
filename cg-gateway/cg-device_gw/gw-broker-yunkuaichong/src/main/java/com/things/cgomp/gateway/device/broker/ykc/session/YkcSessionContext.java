package com.things.cgomp.gateway.device.broker.ykc.session;


import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.common.gw.device.context.broker.IBroker;
import com.things.cgomp.common.gw.device.context.session.BaseSessionContext;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import static com.things.cgomp.common.gw.device.context.session.TcpSessionContext.S_DEVICE_SESSION;


@Slf4j
public class YkcSessionContext extends BaseSessionContext<YkcSession, Channel> {

    /**
     * 随机密钥
     */
    public static AttributeKey<String> DEVICE_SECRET_KEY = AttributeKey.valueOf("device.secretKey");

    public YkcSessionContext(IDeviceServiceApi deviceService, IBroker broker) {
        super(deviceService, broker);
    }


    @Override
    public YkcSession createSession(
            Channel channel,
            String sessionId,
            String clientIp,
            Long deviceId,
            Long productId,
            String sn,
            String deviceConfig
    ) {
        Integer abnormalOffLineTime = getAbnormalOffLineTime();
        YkcSession session = new YkcSession();
        session.setAuth(true);
        session.setDeviceId(deviceId);
        session.setProductId(productId);
        session.setStartTime(System.currentTimeMillis());
        session.setSn(sn);
        session.setChannel(channel);
        session.setClientIp(clientIp);
        session.setSessionId(sessionId);
        session.setAbnormalOffLineTime(abnormalOffLineTime);
        session.setStartTime(System.currentTimeMillis());
        channel.attr(S_DEVICE_SESSION).set(session);
        return session;
    }


}


