package com.things.cgomp.common.datascope.aspect.sql.impl;

import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.datascope.aspect.sql.SqlService;
import com.things.cgomp.common.datascope.utils.SqlUtils;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.domain.SysUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义平台角色数据权限
 *
 * @author things
 */
@Service
public class CustomPlatformRoleSqlServiceImpl extends SqlService {

    @Override
    public String getSql(DataScope dataScope, SysUser user, SysRole role, List<String> scopeCustomIds) {
        StringBuilder sqlString = new StringBuilder();
        String orgAlias = dataScope.orgAlias();
        if (StringUtils.isNotBlank(orgAlias)) {
            // 根据组织过滤
            sqlString.append(SqlUtils.buildOrgIdSql(orgAlias, user.getOrgId()));
        }
        String userOperatorAlias = dataScope.userOperatorAlias();
        if (StringUtils.isNotBlank(userOperatorAlias)) {
            // 根据用户运营商关联过滤
            sqlString.append(SqlUtils.buildUserOperatorSql(userOperatorAlias, user));
        }
        return sqlString.toString();
    }
}