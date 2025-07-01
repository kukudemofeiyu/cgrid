package com.things.cgomp.gateway.device.broker.ykc.service.queue;

import com.alibaba.fastjson.JSON;
import com.things.cgomp.common.device.dao.device.domain.DeviceChargeRecordException;
import com.things.cgomp.common.device.service.DeviceChargeExceptionService;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.consumer.AbrRocketMQTransactionListener;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RocketMQTransactionListener
public class ChargeRecordTransactionListener extends AbrRocketMQTransactionListener<TradingRecordConfirmReqMsg> {

    @Autowired
    private DeviceChargeExceptionService deviceChargeExceptionService;

    @Override
    protected RocketMQLocalTransactionState executeLocalTransaction(TradingRecordConfirmReqMsg msg, Metadata metadata, Object arg) {
        DeviceChargeRecordException deviceChargeRecordException = convert(msg, metadata);

        deviceChargeExceptionService.save(deviceChargeRecordException);

        return RocketMQLocalTransactionState.COMMIT;
    }

    private DeviceChargeRecordException convert(TradingRecordConfirmReqMsg msg, Metadata metadata) {
        return new DeviceChargeRecordException()
                .setOrderNo(msg.getOrderNo())
                .setAmount(msg.getAmount())
                .setElectricity(msg.getElectricity())
                .setCardNo(msg.getCardNo())
                .setCommitTime(new Date())
                .setFlag(msg.getFlag())
                .setVin(msg.getVin())
                .setEndTime(new Date(msg.getEndTime()))
                .setStartTime(new Date(msg.getStartTime()))
                .setPortId(metadata.getPortId())
                .setPileId(metadata.getDeviceId())
                .setOrderTime(new Date(msg.getOrderTime()))
                .setEndReasonCode(msg.getEndReasonCode())
                .setEndReasonDesc(msg.getEndReasonDesc())
                .setFeeRateJson(JSON.toJSONString(msg.getFeeRates()))
                .setEventTs(new Date(metadata.getEventTime()));
    }

    @Override
    protected RocketMQLocalTransactionState checkLocalTransaction(TradingRecordConfirmReqMsg msg, Metadata metadata) {
        Long orderExceptionId = deviceChargeExceptionService.findIdByOrderNo(msg.getOrderNo());
        log.info("回查交易订单记录是否入库 , idByOrderNo :{}, msg:{}", orderExceptionId, msg);
        if(orderExceptionId != null){
            return RocketMQLocalTransactionState.COMMIT;
        }else{
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }
}
