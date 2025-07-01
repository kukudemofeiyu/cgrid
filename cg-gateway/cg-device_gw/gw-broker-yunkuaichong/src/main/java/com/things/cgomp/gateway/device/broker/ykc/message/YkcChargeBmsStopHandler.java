package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcChargeBmsStopIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcChargeErrorStatusIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcMessageOut;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class YkcChargeBmsStopHandler extends YkcAbstractMessageHandler<YkcChargeBmsStopIn, YkcMessageOut>{

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcChargeBmsStopIn message) throws Exception {
        log.info("充电阶段BMS中止:{}", message);
    }

    @Override
    public void write(ByteBuf byteBuf, YkcMessageOut out) {

    }
}
