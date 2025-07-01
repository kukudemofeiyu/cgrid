package com.things.cgomp.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wx.charging")
public class ChargingMessageConfig {
    /**
     * 开始充电模板ID
     */
    private String startChargingTemplateId = "default_start_template_id";

    /**
     * 开始充电跳转地址
     */
    private String startChargingRedirectUrl = "https://default.start.charging.url";

    /**
     * 结束充电模板ID
     */
    private String endChargingTemplateId = "default_end_template_id";

    /**
     * 结束充电跳转地址
     */
    private String endChargingRedirectUrl = "https://default.end.charging.url";
}
