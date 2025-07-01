package com.things.cgomp.common.mq.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 此注解不根据配置是否开启，强制消费
 * @author things
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RocketMQForceConsumer {
}