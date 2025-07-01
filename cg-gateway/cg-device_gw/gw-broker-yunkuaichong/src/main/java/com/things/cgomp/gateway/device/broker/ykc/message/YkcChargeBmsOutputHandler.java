package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.DeviceChargeDataReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcChargeBmsOutputIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcMessageOut;
import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class YkcChargeBmsOutputHandler extends YkcAbstractMessageHandler<YkcChargeBmsOutputIn, YkcMessageOut>{

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcChargeBmsOutputIn message) throws Exception {
        log.info("充电过程BMS需求充电机输出:{}", message);

        DeviceChargeDataReqMsg deviceChargeDataReqMsg = DeviceChargeDataReqMsg.builder()
                .orderSn(message.getOrderNo())
                .bmsOCurrentXQ(message.getBmsCurrentXQ())
                .bmsOVoltageXQ(message.getBmsVoltageXQ())
                .bmsOChargeMode(message.getChargeMode())
                .bmsOVoltageMeasure(message.getBmsVoltageMeasure())
                .bmsOCurrentMeasure(message.getBmsCurrentMeasure())
                .bmsOMaxVoltage(message.getMaxVoltage())
                .bmsOMaxVoltageGroupNo(message.getMaxVoltageGroupNo())
                .bmsOSoc(message.getSoc())
                .bmsOChargeTime(message.getBmsChargeTime())
                .bmsOVoltageOutput(message.getVoltageOutput())
                .bmsOCurrentOutput(message.getCurrentOutput())
                .bmsOTotalChargeTime(message.getTotalChargeTime())
                .build();

        SessionDeviceInfo childDeviceInfo = session.getChildDeviceInfo(message.getGunNo());
        sessionContext.getDeviceService().sendData(MQTopics.DEVICE_CHARGE_DATA, MQTopics.Tag.DEVICE_CHARGE, deviceChargeDataReqMsg, session,
                childDeviceInfo.getDeviceId(), DeviceOpConstantEnum.CHARGE_OUTPUT_STATUS.getOpCode(), message.getTs(), new SimpleServiceCallback<Void>() {
                    @Override
                    public void onSuccess(Void msg) {
                        log.debug("发送充电过程BMS数据成功,msg={}", deviceChargeDataReqMsg);
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.error("发送充电过程BMS数据失败,deviceNo={}", deviceChargeDataReqMsg, e);
                    }
                });

    }

    @Override
    public void write(ByteBuf byteBuf, YkcMessageOut out) {

    }
}
