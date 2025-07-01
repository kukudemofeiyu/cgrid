package com.things.cgomp.device.data.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
public class DeviceDataProperties {

    @Value("${data.persist:true}")
    private Boolean persist;
    @Value("${data.notifyApp:false}")
    private Boolean notifyApp;
}
