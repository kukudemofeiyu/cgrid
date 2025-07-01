package com.things.cgomp.alarm.queue.comsumer;

import com.things.cgomp.alarm.domain.DeviceAlarmInfo;
import com.things.cgomp.alarm.mapper.DeviceAlarmMapper;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.DeviceAlarmMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.ALARM,
        consumerGroup = MQTopics.Group.ALARM
)
public class DeviceAlarmConsumer extends AbrRocketMQConsumer<DeviceAlarmMsg> {

    @Autowired
    private DeviceAlarmMapper deviceAlarmMapper;

    @Override
    protected void onMessage(DeviceAlarmMsg reqMsg, Metadata metadata) {

        if (reqMsg.getIsAlarm()) {
            processAlarm(reqMsg, metadata);
        } else {
            processRecoverAlarm(reqMsg, metadata);
        }

    }

    private void processRecoverAlarm( DeviceAlarmMsg reqMsg, Metadata metadata) {
        DeviceAlarmInfo existAlarm = getExistAlarm(reqMsg, metadata);
        if(existAlarm != null){
            existAlarm.setDealStatus(1);
            existAlarm.setRecoveryType(0);
            existAlarm.setRecoveryTime(new Date());

            int updateRow = deviceAlarmMapper.updateById(existAlarm);
            if(updateRow!=0){
                log.info("恢复告警成功, metadata={}, reqMsg={}", metadata, reqMsg);
            }
        }
    }

    private void processAlarm(DeviceAlarmMsg reqMsg, Metadata metadata) {
        DeviceAlarmInfo existAlarm = getExistAlarm(reqMsg, metadata);
        //去重，重复告警不处理/存在重复告警不再生成告警
        if(existAlarm == null){
            DeviceAlarmInfo addAlarm = new DeviceAlarmInfo();
            addAlarm.setDeviceId(metadata.getDeviceId());
            addAlarm.setPortId(metadata.getPortId());
            addAlarm.setType(reqMsg.getAlarmType());
            addAlarm.setCode(reqMsg.getAlarmCode());
            addAlarm.setReason(reqMsg.getAlarmReason());
            addAlarm.setAlarmTime(DateUtils.toDate(reqMsg.getTs()));
            addAlarm.setDealStatus(0);

            Integer insertRow = deviceAlarmMapper.insert(addAlarm);

            if (insertRow != null) {
                log.info("新增告警成功, metadata={}, reqMsg={}", metadata, reqMsg);
            }
        }
    }


    /**
     * 是否已存在告警
     */
    private DeviceAlarmInfo getExistAlarm(DeviceAlarmMsg reqMsg, Metadata metadata) {

        DeviceAlarmInfo queryDTO = new DeviceAlarmInfo();
        queryDTO.setDeviceId(metadata.getDeviceId());
        queryDTO.setPortId(metadata.getPortId());
        queryDTO.setType(reqMsg.getAlarmType());
        queryDTO.setCode(reqMsg.getAlarmCode());

        DeviceAlarmInfo deviceAlarmInfo = deviceAlarmMapper.findAlarmInfo(queryDTO);
        return deviceAlarmInfo;
    }


}
