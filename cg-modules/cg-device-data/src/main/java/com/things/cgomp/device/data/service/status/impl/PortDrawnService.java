package com.things.cgomp.device.data.service.status.impl;

import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.dao.device.domain.PortChangeValue;
import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.producer.IProducer;
import com.things.cgomp.common.mq.record.service.MqProducerRecordService;
import com.things.cgomp.device.data.convert.PortStatusConvert;
import com.things.cgomp.device.data.service.status.AbrStatusChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author things
 */
@Slf4j
@Service("portDrawnService")
public class PortDrawnService extends AbrStatusChangeService {

    @Resource
    private IProducer producer;
    @Resource
    private MqProducerRecordService producerRecordService;

    @Override
    public void process(DevicePortStatus portStatus, Metadata metadata) {
        PortChangeValue changeValue = portStatus.getChangeValue();
        if (changeValue == null || changeValue.getOrderSn() == null) {
            return;
        }
        // 检查未结算订单
        checkUnSettledOrder(portStatus.getPortId(), changeValue.getOrderSn());
        // 发送占位消息
        sendOccupyMsg(changeValue, metadata);
    }

    private void sendOccupyMsg(PortChangeValue changeValue, Metadata metadata) {
        // 发送占位费结算消息到订单服务
        DrawGunReqMsg reqMsg = PortStatusConvert.INSTANCE.convertToOccupy(changeValue, metadata);
        QueueMsg<AbstractBody> sendMsg = QueueMsg.builder()
                .metadata(metadata)
                .body(reqMsg)
                .build();
        try {
            // 异步发送消息到订单服务
            producer.asyncSend(MQTopics.ORDER, MQTopics.Tag.DRAW_GUN_REQ, sendMsg, new SimpleServiceCallback() {
                @Override
                public void onSuccess(Object msg) {
                    log.info("PortDrawnService 消息发送成功, msg={}", msg);
                }

                @Override
                public void onError(Throwable e) {
                    log.info("PortDrawnService 消息发送失败, 数据库记录数据进行重发, ", e);
                    producerRecordService.saveSendFailRecord(MQTopics.ORDER, MQTopics.Tag.DRAW_GUN_REQ, sendMsg);
                }
            });
        } catch (Exception e) {
            log.error("PortDrawnService error, changeValue={}, metadata={}", changeValue, metadata, e);
            producerRecordService.saveSendFailRecord(MQTopics.ORDER, MQTopics.Tag.DRAW_GUN_REQ, sendMsg);
        }
    }
}
