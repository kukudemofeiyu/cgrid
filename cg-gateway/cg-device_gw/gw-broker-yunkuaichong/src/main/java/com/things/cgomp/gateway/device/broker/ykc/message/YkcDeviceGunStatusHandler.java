package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.DeviceDataReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceStatusInfoIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceStatusInfoOut;
import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 设备枪状态信息处理器
 */
@Slf4j
@Component
public class YkcDeviceGunStatusHandler extends YkcAbstractMessageHandler<YkcDeviceStatusInfoIn, YkcDeviceStatusInfoOut> {

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceStatusInfoIn message) throws Exception {
        log.info("实时数据上报: {}", message);

        DeviceDataReqMsg deviceDataReqMsg = DeviceDataReqMsg.builder().build();
        deviceDataReqMsg.setOrderNo(message.getOrderNo());
        deviceDataReqMsg.setVoltage(message.getVoltage());
        deviceDataReqMsg.setCurrent(message.getCurrent());
        deviceDataReqMsg.setPortSn(String.valueOf(message.getGunNo()));
        deviceDataReqMsg.setSoc(message.getSoc());
        deviceDataReqMsg.setTimeCharge(message.getChargeTime());
        deviceDataReqMsg.setTimeLeft(message.getRemainingTime());
        deviceDataReqMsg.setAmount(message.getChargeAmount());
        deviceDataReqMsg.setChargeEnergy(message.getChargeElectricity());
        deviceDataReqMsg.setPower(message.getVoltage() * message.getCurrent());
        deviceDataReqMsg.setTemperature((double) (message.getBatteryGroupMaxTemp()));
        deviceDataReqMsg.setPortStatus(message.getGunStatus().getStatus());
        deviceDataReqMsg.setPortHoming(message.getGunHomeStatus());
        deviceDataReqMsg.setPortInserted(message.getGunInserted().equals(0)?false:true);
        deviceDataReqMsg.setGunLineTemperature(Double.valueOf(message.getGunLineTemp()));
        deviceDataReqMsg.setPileBodyTemperature(Double.valueOf(message.getDeviceTemp()));

        SessionDeviceInfo childDeviceInfo = session.getChildDeviceInfo(message.getGunNo());
        sessionContext.getDeviceService().sendData(MQTopics.DEVICE_DATA, null, deviceDataReqMsg, session,
                childDeviceInfo.getDeviceId(), DeviceOpConstantEnum.DEVICE_STATUS_REPORT.getOpCode(), message.getTs(), new SimpleServiceCallback<Void>() {
                    @Override
                    public void onSuccess(Void msg) {
                        log.debug("发送枪实时数据成功,msg={}", deviceDataReqMsg);
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.error("发送枪实时数据失败,deviceNo={}", deviceDataReqMsg, e);
                    }
                });

    }

    @Override
    public void write(ByteBuf byteBuf, YkcDeviceStatusInfoOut ykcDeviceStatusInfoOut) {
        writeDeviceNo(byteBuf, ykcDeviceStatusInfoOut.getDeviceNo());
        writeGunNo(byteBuf, ykcDeviceStatusInfoOut.getGunNo());
    }


}
