package com.things.cgomp.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "websocket")
@Data
public class WebsocketProperties {

    private Boolean push;

    private Integer sendTimeLimit;

    private Integer bufferSizeLimit;

    private String topic;

    private Boolean logSwitch;

    /**
     * 1-普通session 2-Concurrent Session
     */
    private Integer type;

    private Boolean batchSend;

    private Integer numPerBatch;

    private Boolean syncSend;

    public WebsocketProperties() {
        this.sendTimeLimit = 10* 1000;
        this.bufferSizeLimit =100;
        this.topic = "websocket_oss";
        this.logSwitch = false;
        this.type = 1;
        this.batchSend =false;
        this.numPerBatch =20;
        this.syncSend = false;
        this.push = true;
    }
}
