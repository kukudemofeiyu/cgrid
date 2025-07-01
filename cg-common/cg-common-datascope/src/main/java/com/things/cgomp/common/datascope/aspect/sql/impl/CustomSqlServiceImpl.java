package com.things.cgomp.common.datascope.aspect.sql.impl;

import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.datascope.aspect.sql.SqlService;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.domain.SysUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义数据权限
 *
 * @author things
 */
@Service
public class CustomSqlServiceImpl extends SqlService {

    @Override
    public String getSql(DataScope dataScope, SysUser user, SysRole role, List<String> scopeCustomIds) {
        StringBuilder sqlString = new StringBuilder();
        String orgAlias = dataScope.orgAlias();
        if (scopeCustomIds.size() > 1) {
            // 多个自定数据权限使用in查询，避免多次拼接。
            sqlString.append(StringUtils.format(" OR {}.org_id IN ( SELECT org_id FROM system_role_org WHERE role_id in ({}) ) ",
                    orgAlias,
                    String.join(",", scopeCustomIds)));
        } else {
            sqlString.append(StringUtils.format(" OR {}.org_id IN ( SELECT org_id FROM system_role_org WHERE role_id = {} ) ",
                    orgAlias,
                    role.getRoleId()));
        }
        return sqlString.toString();
    }
}
