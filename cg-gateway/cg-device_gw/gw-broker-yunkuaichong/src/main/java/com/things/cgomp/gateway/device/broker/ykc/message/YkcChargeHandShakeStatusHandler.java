package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.DeviceChargeHandshakeReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcChargeHandShakeStatusIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcChargeHandShakeStatusOut;
import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class YkcChargeHandShakeStatusHandler extends YkcAbstractMessageHandler<YkcChargeHandShakeStatusIn, YkcChargeHandShakeStatusOut>{

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcChargeHandShakeStatusIn message) throws Exception {
        log.info("充电握手参数:{}", message);


        DeviceChargeHandshakeReqMsg deviceDataReqMsg = DeviceChargeHandshakeReqMsg.builder()
                .vin(message.getVin())
                .build();

        SessionDeviceInfo childDeviceInfo = session.getChildDeviceInfo(message.getGunNo());
        sessionContext.getDeviceService().sendData(MQTopics.DEVICE_EXPAND, MQTopics.Tag.DEVICE_CHARGE_HANDSHAKE, deviceDataReqMsg, session,
                childDeviceInfo.getDeviceId(), DeviceOpConstantEnum.DEVICE_STATUS_REPORT.getOpCode(), message.getTs(), new SimpleServiceCallback<Void>() {
                    @Override
                    public void onSuccess(Void msg) {
                        log.debug("发送充电握手参数成功,msg={}", deviceDataReqMsg);
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.error("发送充电握手参数失败,deviceNo={}", deviceDataReqMsg, e);
                    }
                });



    }

    @Override
    public void write(ByteBuf byteBuf, YkcChargeHandShakeStatusOut out) {

    }
}
