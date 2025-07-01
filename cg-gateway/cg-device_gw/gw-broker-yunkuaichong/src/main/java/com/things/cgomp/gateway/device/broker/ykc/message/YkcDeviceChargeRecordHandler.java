package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.enums.YkcDeviceChargeStopReasonEnum;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.ChargeFeeRate;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceChargeRecordIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceChargeRecordOut;
import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * 设备充电记录处理器
 */
@Slf4j
@Component
public class YkcDeviceChargeRecordHandler extends YkcAbstractMessageHandler<YkcDeviceChargeRecordIn, YkcDeviceChargeRecordOut> {

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceChargeRecordIn message) throws Exception {
        log.info("交易记录上报 : {}", message);

        TradingRecordConfirmReqMsg reqMsg = new TradingRecordConfirmReqMsg();
        reqMsg.setOrderNo(message.getOrderNo());
        reqMsg.setCardNo(message.getCardNo());
        reqMsg.setStartTime(message.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        reqMsg.setEndTime(message.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        reqMsg.setElectricity(message.getChargeElectricity());
        reqMsg.setAmount(message.getChargeFee());
        reqMsg.setFlag(message.getOrderChargeType());
        reqMsg.setOrderTime(message.getOrderTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        reqMsg.setVin(message.getVin());
        reqMsg.setTransactionId(UUID.randomUUID().toString());
        reqMsg.setFeeRates(convertFee(message.getChargeRecordFees()));

        //交易记录停止原因
        YkcDeviceChargeStopReasonEnum deviceChargeStopReasonEnum = YkcDeviceChargeStopReasonEnum.getDeviceChargeStopReasonEnum(message.getStopReason());
        if(deviceChargeStopReasonEnum != null){
            reqMsg.setEndReasonCode(deviceChargeStopReasonEnum.getReason());
            reqMsg.setEndReasonDesc(deviceChargeStopReasonEnum.getReasonDesc());
        }else{
            reqMsg.setEndReasonCode(message.getStopReason());
            reqMsg.setEndReasonDesc("未定义原因");
        }

        SessionDeviceInfo childDeviceInfo = session.getChildDeviceInfo(message.getGunNo());
        if(childDeviceInfo == null){
            DeviceInfo deviceInfo = sessionContext.getDeviceService().getDeviceInfo(new DeviceInfo().setParentId(session.getDeviceId()).setAliasSn(message.getGunNo()));
            childDeviceInfo = SessionDeviceInfo.builder().deviceId(deviceInfo.getDeviceId()).build();
        }

        Boolean transactionCommitResult = sessionContext.getDeviceService().sendOrderRecordTransactionMsg(MQTopics.ORDER, MQTopics.Tag.TRADING_RECORD_CONFIRM_REQ, reqMsg, session,
                childDeviceInfo.getDeviceId(), DeviceOpConstantEnum.CHARGE_RECORD.getOpCode(), message.getTs());


        if(!transactionCommitResult){
            log.error("发送交易记录失败,message={}", message);
            return;
        }

        if(transactionCommitResult){
            //回复设备收到交易记录
            log.info("发送交易记录成功,message={}", message);
            sendAckMsg(sessionContext, ctx.channel(), session,
                    YkcDeviceChargeRecordOut.of(message.getFrameSerialNo(),
                            message.getOrderNo(),
                            Boolean.TRUE));
        }



    }

    private List<ChargeFeeRate> convertFee(List<YkcDeviceChargeRecordIn.ChargeRecordFee> chargeRecordFees) {
        return chargeRecordFees.stream().map(f1 -> {
            ChargeFeeRate chargeFeeRate = new ChargeFeeRate();
            chargeFeeRate.setUnitPrice(f1.getSingleFee());
            chargeFeeRate.setEnergy(f1.getElectricity());
            chargeFeeRate.setLoseEnergy(f1.getLossElectricity());
            chargeFeeRate.setAmount(f1.getTotalFee());
            return chargeFeeRate;
        }).collect(Collectors.toList());

    }


    @Override
    public void write(ByteBuf byteBuf, YkcDeviceChargeRecordOut ykcDeviceChargeRecordOut) {
        writeOrderNo(byteBuf, ykcDeviceChargeRecordOut.getOrderNo());
        byteBuf.writeByte(ykcDeviceChargeRecordOut.getAck() ? 0x00 : 0x01);
    }

}
