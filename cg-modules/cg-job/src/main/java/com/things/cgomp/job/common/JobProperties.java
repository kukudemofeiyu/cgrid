package com.things.cgomp.job.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
public class JobProperties {

    /**
     * mq消息重试每次从数据库中获取的数量
     */
    @Value("${job.mqRetryRecordLimit:50}")
    private Integer mqRetryRecordLimit;
}
