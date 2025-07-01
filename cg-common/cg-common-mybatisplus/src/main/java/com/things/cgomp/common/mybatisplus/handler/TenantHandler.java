package com.things.cgomp.common.mybatisplus.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.things.cgomp.common.core.context.ISecurityContext;
import com.things.cgomp.common.mybatisplus.common.TenantProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

/**
 * 租户处理器
 */
@Slf4j
@AllArgsConstructor
public class TenantHandler implements TenantLineHandler {

    private final ISecurityContext securityContext;

    private final TenantProperties properties;

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    @Override
    public Expression getTenantId() {
        return new LongValue(securityContext.getTenantId());
    }

    /**
     * 获取租户字段名称
     *
     * @return 租户字段名称
     */
    @Override
    public String getTenantIdColumn() {
        return properties.getColumn();
    }

    /**
     * 过滤租户表
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
     */
    @Override
    public boolean ignoreTable(String tableName) {
        return properties.getIgnoreTables().contains(tableName);
    }
}
