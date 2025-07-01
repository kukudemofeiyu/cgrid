package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.core.exception.ErrorCode;
import com.things.cgomp.common.device.pojo.device.push.PushResult;
import com.things.cgomp.common.gw.device.context.api.SyncMsgTemplate;
import com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceStartChargeIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceStartChargeOut;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants.*;

/**
 * 开始充电
 */
@Slf4j
@Component
public class YkcDeviceStartChargeHandler extends YkcAbstractMessageHandler<YkcDeviceStartChargeIn, YkcDeviceStartChargeOut> {

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceStartChargeIn message) throws Exception {
        log.info("启动充电回复: {}", message);


        processResult(sessionContext, message.getControlSuccessful(),
                message.getDeviceNo(), message.getFrameSerialNo(),
                message.getErrorReason());
    }

    @Override
    public void write(ByteBuf byteBuf, YkcDeviceStartChargeOut ykcDeviceStartChargeOut) {
        log.info("启动充电: {}", ykcDeviceStartChargeOut);
        // 订单编号
        writeOrderNo(byteBuf, ykcDeviceStartChargeOut.getOrderNo());
        // 设备编号
        writeDeviceNo(byteBuf, ykcDeviceStartChargeOut.getDeviceNo());
        // 枪号
        writeGunNo(byteBuf, ykcDeviceStartChargeOut.getGunNo());
        // 逻辑卡号
        byteBuf.writeBytes(getCard(ykcDeviceStartChargeOut.getCardNo()));
        // 物理卡号
        byteBuf.writeBytes(getIccid(ykcDeviceStartChargeOut.getIccid()));
        // 账户余额
        byteBuf.writeIntLE(ykcDeviceStartChargeOut.getBalance());
        // 本次充电当前允许的最大功率,
        byteBuf.writeBytes(new byte[]{00, 00});
        // SOC 限制
        byteBuf.writeBytes(new byte[]{00});
        // 充电电量限制
        byteBuf.writeBytes(new byte[]{00, 00, 00 ,00});

    }

    private byte[] getIccid(String iccid) {
        return new byte[]{00, 00,00,00,00,00,00,00};
    }

    private byte[] getCard(String cardNo) {
        return new byte[]{00, 00,00,00,00,00,00,00};
    }



}
