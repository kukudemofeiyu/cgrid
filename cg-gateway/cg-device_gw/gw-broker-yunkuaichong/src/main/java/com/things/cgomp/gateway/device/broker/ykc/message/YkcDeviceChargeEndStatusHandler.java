package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceChargeEndStatusIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcMessageOut;
import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Slf4j
@Component
public class YkcDeviceChargeEndStatusHandler extends YkcAbstractMessageHandler<YkcDeviceChargeEndStatusIn, YkcMessageOut> {

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceChargeEndStatusIn message) throws Exception {
        log.info("充电结束: {}", message);

        EndChargingReqMsg endChargingReqMsg = new EndChargingReqMsg();
        endChargingReqMsg.setOrderNo(message.getOrderNo());
        endChargingReqMsg.setEndTime(message.getTs());

        SessionDeviceInfo childDeviceInfo = session.getChildDeviceInfo(message.getGunNo());

        sessionContext.getDeviceService().sendData(MQTopics.ORDER, MQTopics.Tag.ORDER_END_CHARGING, endChargingReqMsg, session, childDeviceInfo.getDeviceId(), DeviceOpConstantEnum.DEVICE_CHARGE_END.getOpCode(), message.getTs(), new SimpleServiceCallback<Void>() {
            @Override
            public void onSuccess(Void msg) {
                log.debug("发送充电结束消息成功,msg={}", endChargingReqMsg );
            }

            @Override
            public void onError(Throwable e) {
                log.error("发送充电结束消息失败,deviceNo={}", endChargingReqMsg,e  );
            }
        });
    }

    @Override
    public void write(ByteBuf byteBuf, YkcMessageOut out) {

    }
}
