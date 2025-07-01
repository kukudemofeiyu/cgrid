package com.things.cgomp.common.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.github.pagehelper.dialect.helper.PostgreSqlDialect;
import com.github.pagehelper.page.PageAutoDialect;
import com.things.cgomp.common.core.context.ISecurityContext;
import com.things.cgomp.common.mybatisplus.common.TenantProperties;
import com.things.cgomp.common.mybatisplus.handler.DefaultDBFieldHandler;
import com.things.cgomp.common.mybatisplus.handler.TenantHandler;
import com.things.cgomp.common.mybatisplus.interceptor.PaginationInnerInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis Plus 配置
 *
 * @author things
 */
@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({TenantProperties.class})
public class MybatisPlusConfig
{
    /**
      * 多租户配置类
     */
    private final TenantProperties tenantProperties;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(@Autowired(required = false) ISecurityContext securityContext)
    {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if (securityContext != null) {
            //租户插件必须在分页插件之前
            if (tenantProperties != null && tenantProperties.getEnable().equals(true)) {
                // 租户插件
                interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantHandler(securityContext, tenantProperties)));
            }
        }
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        // 阻断插件
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor());
        // 手动往PageHelper添加td数据源方言
        PageAutoDialect.registerDialectAlias("taos-rs", PostgreSqlDialect.class);
        return interceptor;
    }

    /**
     * 分页插件，自动识别数据库类型 https://baomidou.com/guide/interceptor-pagination.html
     */
    public PaginationInnerInterceptor paginationInnerInterceptor()
    {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置数据库类型为mysql
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(-1L);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件 https://baomidou.com/guide/interceptor-optimistic-locker.html
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor()
    {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 如果是对全表的删除或更新操作，就会终止该操作 https://baomidou.com/guide/interceptor-block-attack.html
     */
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor()
    {
        return new BlockAttackInnerInterceptor();
    }

    @Bean
    public MetaObjectHandler defaultMetaObjectHandler(@Autowired(required = false) ISecurityContext securityContext){
        return new DefaultDBFieldHandler(securityContext); // 自动填充参数类
    }
}
