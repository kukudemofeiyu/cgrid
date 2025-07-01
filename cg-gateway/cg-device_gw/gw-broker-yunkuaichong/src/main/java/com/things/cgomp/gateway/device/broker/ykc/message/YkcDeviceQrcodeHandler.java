package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceQrcodeIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceQrcodeOut;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 二维码处理器
 */
@Slf4j
@Component
public class YkcDeviceQrcodeHandler extends YkcAbstractMessageHandler<YkcDeviceQrcodeIn, YkcDeviceQrcodeOut> {

    @Value("${ykc.qrcodePrefix}")
    private String qrcodePrefix;

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceQrcodeIn message) {
        log.info("二维码设置回复: {}", message);
        Boolean qrCodeResult = message.getQrCodeResult();
        if(qrCodeResult){
            SessionDeviceInfo gunDevice = session.getChildDeviceInfo(message.getGunNo());
            if(gunDevice != null){
                gunDevice.setQrSetResult(true);
            }
        }
    }

    @Override
    public void write(ByteBuf byteBuf, YkcDeviceQrcodeOut out) {
        String qrcodeUrl = qrcodePrefix + out.getPortId();
        log.info("设置二维码: {}, qrcodeUrl:{}", out, qrcodeUrl);
        writeDeviceNo(byteBuf, out.getDeviceNo());
        writeGunNo(byteBuf, out.getGunNo());
        byteBuf.writeByte(0x00); //0x00 QR 码
        byteBuf.writeByte(qrcodeUrl.length());
        byteBuf.writeCharSequence(qrcodeUrl, StandardCharsets.US_ASCII);
    }
}
