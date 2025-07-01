package com.things.cgomp.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置信息
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileConfig {

    /**
     * 存储器
     */
    private String storage;
}
