package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceTimeSyncIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceTimeSyncOut;
import com.things.cgomp.gateway.device.broker.ykc.utils.Cp56Time2aUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 时间同步处理器
 */
@Slf4j
@Component
public class YkcTimeSyncHandler extends YkcAbstractMessageHandler<YkcDeviceTimeSyncIn, YkcDeviceTimeSyncOut> {

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceTimeSyncIn message) throws Exception {
        log.info("校时回复: {}", message);
    }

    @Override
    public void write(ByteBuf byteBuf, YkcDeviceTimeSyncOut ykcDeviceTimeSyncOut) {
        log.info("校时:date:{}",ykcDeviceTimeSyncOut.getTime());
        writeDeviceNo(byteBuf, ykcDeviceTimeSyncOut.getDeviceNo());
        byteBuf.writeBytes(Cp56Time2aUtils.encodeCP56Time2a(ykcDeviceTimeSyncOut.getTime()));
    }
}
