package com.things.cgomp.device.data.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author things
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "persist-queue")
public class PersistQueueProperties {

    private Map<String, PersistConsumer> consumers;

}
