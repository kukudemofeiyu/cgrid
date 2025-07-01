package com.things.cgomp.common.mq.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 此注解根据配置是否开启mq消费
 * @author things
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
@ConditionalOnProperty(prefix = "rocketmq.consumer", value = "enable", havingValue = "true")
public @interface RocketMQConsumer {
}