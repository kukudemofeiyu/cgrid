package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcChargeConfigSettingIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcChargeErrorStatusIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcMessageOut;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class YkcChargeErrorStatusHandler extends YkcAbstractMessageHandler<YkcChargeErrorStatusIn, YkcMessageOut>{

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcChargeErrorStatusIn message) throws Exception {
        log.info("充电错误报文:{}", message);
    }

    @Override
    public void write(ByteBuf byteBuf, YkcMessageOut out) {

    }
}
