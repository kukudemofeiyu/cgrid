package com.things.cgomp.file.client.minio;

import com.things.cgomp.file.client.FileClientConfig;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

/**
 * MINIO 文件客户端的配置类
 *
 */
@Data
@Configuration
@ConditionalOnExpression("'${file.storage:null}'=='minio'")
@ConfigurationProperties(prefix = "file.minio")
public class MinioClientConfig implements FileClientConfig {

    /**
     * 节点地址
     */
    @NotNull(message = "endpoint 不能为空")
    private String endpoint;
    /**
     * 自定义域名
     * 通过 Nginx 配置
     */
    @URL(message = "domain 必须是 URL 格式")
    private String domain;
    /**
     * 存储 Bucket
     */
    @NotNull(message = "bucket 不能为空")
    private String bucket;
    /**
     * 访问 Key
     */
    @NotNull(message = "accessKey 不能为空")
    private String accessKey;
    /**
     * 访问 Secret
     */
    @NotNull(message = "accessSecret 不能为空")
    private String accessSecret;
    /**
     * 预签名有效时间（秒）
     * 默认10分钟
     */
    private Integer preSignedDuration = 10 * 60;
}
