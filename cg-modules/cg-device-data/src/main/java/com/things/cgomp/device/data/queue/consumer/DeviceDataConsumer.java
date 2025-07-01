package com.things.cgomp.device.data.queue.consumer;

import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.dao.td.domain.PortStatusData;
import com.things.cgomp.common.device.enums.PortStatusOperate;
import com.things.cgomp.common.device.pojo.device.DevicePortStatusDTO;
import com.things.cgomp.common.device.service.DevicePortStatusService;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceChargeDataMsg;
import com.things.cgomp.common.mq.message.DeviceDataReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.producer.IProducer;
import com.things.cgomp.device.data.common.DeviceDataProperties;
import com.things.cgomp.device.data.convert.DeviceDataConvert;
import com.things.cgomp.device.data.convert.PortStatusConvert;
import com.things.cgomp.device.data.persistence.queue.PortDataPersistMessageService;
import com.things.cgomp.device.data.persistence.queue.PortStatusPersistMessageService;
import com.things.cgomp.device.data.service.status.StatusChangeService;
import com.things.cgomp.device.data.service.status.StatusChangeServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import javax.annotation.Resource;

import static com.things.cgomp.common.device.service.DevicePortStatusService.isEmptyOrder;

/**
 * 设备数据消息消费者
 *
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.DEVICE_DATA,
        consumerGroup = MQTopics.Group.DEVICE_DATA
)
public class DeviceDataConsumer extends AbrRocketMQConsumer<DeviceDataReqMsg> {

    @Resource
    private IProducer producer;
    @Resource
    private DeviceDataProperties properties;
    @Resource
    private DevicePortStatusService devicePortStatusService;
    @Resource
    private PortDataPersistMessageService portDataPersistMessageService;
    @Resource
    private PortStatusPersistMessageService portStatusPersistMessageService;
    @Resource
    private StatusChangeServiceFactory statusChangeServiceFactory;

    @Override
    protected void onMessage(DeviceDataReqMsg reqMsg, Metadata metadata) {
        try {
            // 处理充电枪状态
            PortStatusData portStatus = processPortStatus(reqMsg, metadata);
            if (properties.getPersist()) {
                if (portStatus != null) {
                    // 保存充电枪状态
                    portStatusPersistMessageService.persist(portStatus);
                }
                // 订单号为空时不处理监测数据
                if (!isEmptyOrder(reqMsg.getOrderNo())) {
                    DevicePortData portData = DeviceDataConvert.INSTANCE.convert(reqMsg, metadata);
                    // 持久化消息
                    portDataPersistMessageService.persist(portData);
                }
            }
            if (properties.getNotifyApp()) {
                // 通知APP
                notifyApp(reqMsg, metadata);
            }
        } catch (Exception e) {
            log.error("DeviceDataConsumer 设备数据处理异常，reqMsg={}, metadata={}", reqMsg, metadata, e);
            throw new RuntimeException("设备数据处理异常");
        }
    }

    private PortStatusData processPortStatus(DeviceDataReqMsg reqMsg, Metadata metadata) {
        DevicePortStatus modifyStatus = checkStatus(reqMsg, metadata);
        if (modifyStatus != null) {
            StatusChangeService changeService = statusChangeServiceFactory.getChange(modifyStatus.getChangeFlag());
            if (changeService != null) {
                changeService.process(modifyStatus, metadata);
            }
            return PortStatusConvert.INSTANCE.convertToPersist(modifyStatus, metadata.getEventTime());
        }
        return null;
    }

    private DevicePortStatus checkStatus(DeviceDataReqMsg reqMsg, Metadata metadata) {
        if (metadata.getPortId() == null) {
            return null;
        }
        // 更新枪口状态
        DevicePortStatusDTO checkReq = DevicePortStatusDTO.builder()
                .portId(metadata.getPortId())
                .status(reqMsg.getPortStatus())
                .orderSn(reqMsg.getOrderNo())
                .eventTime(metadata.getEventTime())
                .portInserted(reqMsg.getPortInserted())
                .portHoming(reqMsg.getPortHoming())
                .build();
        return devicePortStatusService.checkAndModifyPortStatus(checkReq, PortStatusOperate.report);
    }

    private void notifyApp(DeviceDataReqMsg reqMsg, Metadata metadata) {
        try {
            if (StringUtils.isEmpty(reqMsg.getOrderNo())) {
                // 无订单号数据不推送
                return;
            }
            DeviceChargeDataMsg chargeMsg = DeviceDataConvert.INSTANCE.convertCharge(reqMsg);
            QueueMsg<AbstractBody> sendMsg = QueueMsg.builder()
                    .metadata(metadata)
                    .body(chargeMsg)
                    .build();
            producer.asyncSend(MQTopics.APP, MQTopics.Tag.CHARGE_INFO, sendMsg, new SimpleServiceCallback() {
                @Override
                public void onSuccess(Object msg) {
                    log.info("notifyApp 消息发送成功, msg={}", msg);
                }

                @Override
                public void onError(Throwable e) {
                    log.info("notifyApp 消息发送失败, ", e);
                    // 发送失败暂时不重新发送
                }
            });
        } catch (Exception e) {
            log.error("notifyApp error, reqMsg={}, metadata={}", reqMsg, metadata, e);
            // 处理失败暂时不抛出异常
        }
    }
}
