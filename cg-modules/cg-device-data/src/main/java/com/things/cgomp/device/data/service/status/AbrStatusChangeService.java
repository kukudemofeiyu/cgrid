package com.things.cgomp.device.data.service.status;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.device.domain.DeviceConnectStatus;
import com.things.cgomp.common.device.dao.device.mapper.DeviceConnectStatusMapper;
import com.things.cgomp.common.core.utils.uuid.UUID;
import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.message.order.TradingRecordCallReqMsg;
import com.things.cgomp.common.mq.producer.IProducer;
import com.things.cgomp.common.mq.record.service.MqProducerRecordService;
import com.things.cgomp.order.api.RemoteOrderService;
import com.things.cgomp.order.api.dto.UnSettledOrderInfo;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author things
 */
@Slf4j
public abstract class AbrStatusChangeService implements StatusChangeService {

    @Resource
    private IProducer producer;
    @Resource
    private RemoteOrderService remoteOrderService;
    @Resource
    private MqProducerRecordService producerRecordService;
    @Resource
    private DeviceConnectStatusMapper deviceConnectStatusMapper;

    protected void checkUnSettledOrder(Long portId, String orderSn){
        // 查询是否存在未结算的订单
        R<UnSettledOrderInfo> orderR = remoteOrderService.getUnsettledOrder(portId, orderSn, SecurityConstants.INNER);
        if (!R.isSuccess(orderR)) {
            log.error("查询未结算的订单异常，portId={}", portId);
        }
        UnSettledOrderInfo order = orderR.getData();
        if (order == null || order.getSn() == null) {
            return;
        }
        // 发送MQ消息
        sendMqMessage(order);
    }

    private void sendMqMessage(UnSettledOrderInfo order) {
        // 获取消息主题
        String topic = getMessageTopic(order.getPileId());
        if (topic == null) {
            log.warn("checkUnSettledOrder 获取消息主题失败, deviceId={}", order.getPileId());
            return;
        }
        // 请求订单交易记录召唤
        TradingRecordCallReqMsg reqMsg = TradingRecordCallReqMsg.builder()
                .orderSn(order.getSn())
                .pileId(order.getPileId())
                .portId(order.getPortId())
                .build();
        QueueMsg<AbstractBody> sendMsg = QueueMsg.builder()
                .metadata(Metadata.builder().eventTime(System.currentTimeMillis())
                        .txId(UUID.randomUUID().toString()).build())
                .body(reqMsg)
                .build();
        try {
            // 异步发送消息
            producer.asyncSend(topic, MQTopics.Tag.TRADING_RECORD_CALL, sendMsg, new SimpleServiceCallback() {
                @Override
                public void onSuccess(Object msg) {
                    log.info("checkUnSettledOrders 订单交易记录召唤消息发送成功, msg={}", msg);
                }

                @Override
                public void onError(Throwable e) {
                    log.info("checkUnSettledOrders 订单交易记录召唤消息发送失败, 数据库记录数据进行重发, ", e);
                    producerRecordService.saveSendFailRecord(topic, MQTopics.Tag.TRADING_RECORD_CALL, sendMsg);
                }
            });
        } catch (Exception e) {
            log.error("checkUnSettledOrders 订单交易记录召唤请求error, reqMsg = {}", reqMsg, e);
            producerRecordService.saveSendFailRecord(topic, MQTopics.Tag.TRADING_RECORD_CALL, sendMsg);
        }
    }

    private String getMessageTopic(Long pileId) {
        // 查询充电桩所在网关节点
        DeviceConnectStatus connectStatus = deviceConnectStatusMapper.selectByDeviceId(pileId);
        if (connectStatus == null) {
            return null;
        }
        return connectStatus.getNodeId();
    }
}