package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.DeviceAlarmMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceAlarmStatusIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceAlarmStatusOut;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class YkcDeviceAlarmStatusHandler extends YkcAbstractMessageHandler<YkcDeviceAlarmStatusIn, YkcDeviceAlarmStatusOut>{

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceAlarmStatusIn message) throws Exception {
        log.info("收到告警:message:{}", message);

        DeviceAlarmMsg deviceAlarmMsg = DeviceAlarmMsg.builder()
                .isAlarm(true)
                .alarmType(message.getAlarmType())
                .alarmReason(message.getAlarmReason())
                .alarmCode(message.getAlarmCode())
                .ts(message.getTs())
                .build();

        SessionDeviceInfo childDeviceInfo = session.getChildDeviceInfo(message.getGunNo());

        sessionContext.getDeviceService().sendData(MQTopics.ALARM, null, deviceAlarmMsg, session,
                childDeviceInfo!=null?childDeviceInfo.getDeviceId():null, message.getMessageType(), message.getTs(), new SimpleServiceCallback<Void>() {
                    @Override
                    public void onSuccess(Void msg) {
                        log.info("发送告警消息成功, deviceNo={},gunNo={}, message:{}", message.getDeviceNo(), message.getGunNo(),
                                deviceAlarmMsg);
                        YkcDeviceAlarmStatusOut ykcDeviceAlarmStatusOut =
                                new YkcDeviceAlarmStatusOut(message.getFrameSerialNo(), message.getDeviceNo(),
                                        message.getGunNo(), true);

                        sendAckMsg(sessionContext, ctx.channel(), session, ykcDeviceAlarmStatusOut);
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.error("发送告警消息失败, deviceNo={},gunNo={}, message:{}", message.getDeviceNo(), message.getGunNo());
                        YkcDeviceAlarmStatusOut ykcDeviceAlarmStatusOut =
                                new YkcDeviceAlarmStatusOut(message.getFrameSerialNo(), message.getDeviceNo(),
                                        message.getGunNo(), false);

                        sendAckMsg(sessionContext, ctx.channel(), session, ykcDeviceAlarmStatusOut);
                    }
                });


    }

    @Override
    public void write(ByteBuf byteBuf, YkcDeviceAlarmStatusOut out) {
        log.info("告警回复：message:{}", out);
        writeDeviceNo(byteBuf, out.getDeviceNo());
        writeGunNo(byteBuf, out.getGunNo());
        byteBuf.writeByte(out.getAck()?0x00:0x01);
    }
}
