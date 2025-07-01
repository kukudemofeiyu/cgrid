package com.things.cgomp.job.schedule;

import com.things.cgomp.common.mq.producer.IProducer;
import com.things.cgomp.common.mq.record.domain.MqProducerFailRecord;
import com.things.cgomp.common.mq.record.service.MqProducerRecordService;
import com.things.cgomp.job.common.JobProperties;
import com.things.cgomp.job.service.LockService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * mq消息重发任务
 *
 * @author things
 */
@Slf4j
@Component
public class MqMessageRetryJob {

    @Resource
    private LockService lockService;
    @Resource
    private JobProperties jobProperties;
    @Resource
    private IProducer producer;
    @Resource
    private MqProducerRecordService mqProducerRecordService;

    private static final String mqRetryLockKey = "job-mq_retry_lock";

    @XxlJob("checkMqRetry")
    public void checkMqRetry() {
        RLock lock = lockService.getLock(mqRetryLockKey);
        if (lock == null) {
            return;
        }
        try {
            // 根据配置获取单次处理最大数量
            List<MqProducerFailRecord> records = mqProducerRecordService.selectRecordList(jobProperties.getMqRetryRecordLimit());
            if (CollectionUtils.isEmpty(records)) {
                return;
            }
            // 重发消息
            Pair<List<Long>, List<Long>> resultPair = resendMessage(records);
            List<Long> successList = resultPair.getLeft();
            List<Long> failList = resultPair.getRight();
            if (!CollectionUtils.isEmpty(successList)) {
                // 删除已成功的记录
                mqProducerRecordService.removeRecords(successList);
            }
            if (!CollectionUtils.isEmpty(failList)) {
                // 修改失败记录
                mqProducerRecordService.updateRecords(failList);
            }
        } catch (Exception e) {
            log.error("MqMessageRetryJob 任务处理失败, ", e);
            throw e;
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    private Pair<List<Long>, List<Long>> resendMessage(List<MqProducerFailRecord> records) {
        List<Long> successRecords = new ArrayList<>();
        List<Long> failRecords = new ArrayList<>();
        try {
            records.forEach(record -> {
                // MQ消息同步发送
                boolean success = producer.syncSend(record.getTopic(), record.getTag(), record.getMessage());
                if (success) {
                    log.info("checkMqRetry asyncSend success, topic={}, tag={}, msg={}, formatMsg={}",
                            record.getTopic(), record.getTag(), record.getMessage(), record.getMessageFormat());
                    successRecords.add(record.getId());
                } else {
                    log.info("checkMqRetry asyncSend fail, topic={}, tag={}, msg={}, formatMsg={}",
                            record.getTopic(), record.getTag(), record.getMessage(), record.getMessageFormat());
                    failRecords.add(record.getId());
                }
            });
        } catch (Exception e) {
            // 打印日志，等待下一次任务再执行
            log.error("MqMessageRetryJob checkMqRetry asyncSend 未全部发送，发送过程中出现异常, ", e);
        }
        return Pair.of(successRecords, failRecords);
    }
}