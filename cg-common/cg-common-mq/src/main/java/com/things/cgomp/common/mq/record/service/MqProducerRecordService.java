package com.things.cgomp.common.mq.record.service;

import com.things.cgomp.common.core.utils.JacksonUtils;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.common.RocketMQHelper;
import com.things.cgomp.common.mq.record.domain.MqProducerFailRecord;
import com.things.cgomp.common.mq.record.mapper.MqProducerFailRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.things.cgomp.common.core.utils.SpringUtils.getAopProxy;

/**
 * @author things
 */
@Slf4j
@Service
public class MqProducerRecordService {

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private MqProducerFailRecordMapper mqProducerFailRecordMapper;

    public void saveSendFailRecord(String topic, QueueMsg<?> message){
        getAopProxy(this).saveSendFailRecord(topic, null, message);
    }

    public void saveSendFailRecord(String topic, String tag, QueueMsg<?> message){
        MqProducerFailRecord record = MqProducerFailRecord.builder()
                .topic(topic)
                .tag(tag)
                .message(RocketMQHelper.getCodec().serialize(message))
                .messageFormat(JacksonUtils.toJsonString(message))
                .modules(applicationContext.getId())
                .build();
        getAopProxy(this).saveSendFailRecord(record);
    }

    /**
     * 保存mq失败记录
     * 添加重试处理，最大重试次数5次，延迟2秒
     * @param record 记录
     */
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(value = 2000, multiplier = 2))
    public void saveSendFailRecord(MqProducerFailRecord record){
        mqProducerFailRecordMapper.insert(record);
    }

    /**
     * 重试后仍失败回调方法
     * @param e      错误
     * @param record 记录对象
     */
    @Recover
    public void recover(Exception e, MqProducerFailRecord record){
        log.error("MqProducerRecordService saveSendFailRecord 保存失败MQ记录失败，record={}, ", record, e);
        // 重试后仍失败，此处可发送短信、邮件通知等。。。
    }

    public List<MqProducerFailRecord> selectRecordList(Integer limit){
        return mqProducerFailRecordMapper.selectListLimit(limit);
    }

    public void removeRecords(List<Long> ids){
        mqProducerFailRecordMapper.deleteBatchIds(ids);
    }

    public void updateRecords(List<Long> ids){
        mqProducerFailRecordMapper.updateRecords(ids);
    }
}
