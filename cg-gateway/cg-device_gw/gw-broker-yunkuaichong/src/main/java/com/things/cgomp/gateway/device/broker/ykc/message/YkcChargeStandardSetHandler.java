package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcChargeStandardSetIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcChargeStandardOut;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcChargeStandardSetOut;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class YkcChargeStandardSetHandler extends YkcAbstractMessageHandler<YkcChargeStandardSetIn, YkcChargeStandardSetOut>{
    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcChargeStandardSetIn message) throws Exception {
        log.info("设置计费模型回复: message:{}", message);
        processResult(sessionContext, message.getControlSuccessful(),
                message.getDeviceNo(), message.getFrameSerialNo(),
                null);
    }

    @Override
    public void write(ByteBuf byteBuf, YkcChargeStandardSetOut out) {
        log.info("设置计费模型: message:{}", out);
        writeChargeStandOut(byteBuf, out.getDeviceNo(), out.getChargeStandardModelNo(),
                out.getFeeSize(),out.getFeeOutList(),out.getLossCalculationRatio(),out.getYkcTimeRangeFeeRate());
    }
}
