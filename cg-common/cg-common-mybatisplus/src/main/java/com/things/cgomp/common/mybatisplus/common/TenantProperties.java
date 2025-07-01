package com.things.cgomp.common.mybatisplus.common;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * 多租户配置
 *
 * @author things
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "cg.tenant")
@Component
public class TenantProperties {
    /**
     * 多租户字段名称
     */
    private String column = "tenant_id";

    /**
     * 租户是否开启
     */
    private static final Boolean ENABLE_DEFAULT = false;
    /**
     * 是否开启
     */
    private Boolean enable = ENABLE_DEFAULT;
    /**
     * 多租户需要过滤的数据表(包含系统表)
     */
    private Set<String> ignoreTables = Collections.emptySet();
    /**
     * 需要忽略多租户的请求
     */
    private Set<String> ignoreUrls = Collections.emptySet();
    /**
     * 需要忽略多租户的 Spring Cache 缓存
     */
    private Set<String> ignoreCaches = Collections.emptySet();
}
