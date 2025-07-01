package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.core.exception.ErrorCode;
import com.things.cgomp.common.device.pojo.device.push.PushResult;
import com.things.cgomp.common.gw.device.context.api.SyncMsgTemplate;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceStopChargeIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceStopChargeOut;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants.*;

/**
 * 停止充电处理器
 */
@Slf4j
@Component
public class YkcDeviceStopChargeHandler extends YkcAbstractMessageHandler<YkcDeviceStopChargeIn, YkcDeviceStopChargeOut> {

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceStopChargeIn message) throws Exception {
        log.info("停止充电回复: {}", message);

        processResult(sessionContext, message.getControlSuccessful(),
                message.getDeviceNo(), message.getFrameSerialNo(),
                message.getErrorReason());
    }

    @Override
    public void write(ByteBuf byteBuf, YkcDeviceStopChargeOut ykcDeviceStopChargeOut) {
        log.info("停止充电参数: {}", ykcDeviceStopChargeOut);
        writeDeviceNo(byteBuf, ykcDeviceStopChargeOut.getDeviceNo());
        writeGunNo(byteBuf, ykcDeviceStopChargeOut.getGunNo());
    }


}
