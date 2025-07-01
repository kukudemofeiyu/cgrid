package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceGetChargeRecordIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceGetChargeRecordOut;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class YkcDeviceChargeRecordGetHandler extends YkcAbstractMessageHandler<YkcDeviceGetChargeRecordIn, YkcDeviceGetChargeRecordOut>{

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceGetChargeRecordIn message) throws Exception {
        log.info("召唤交易记录回复, message:{}", message);
    }

    @Override
    public void write(ByteBuf byteBuf, YkcDeviceGetChargeRecordOut out) {
        log.info("召唤交易记录, message:{}", out);
        writeOrderNo(byteBuf, out.getOrderNo());
        writeDeviceNo(byteBuf,out.getDeviceNo());
        writeGunNo(byteBuf, out.getGunNo());
    }
}
