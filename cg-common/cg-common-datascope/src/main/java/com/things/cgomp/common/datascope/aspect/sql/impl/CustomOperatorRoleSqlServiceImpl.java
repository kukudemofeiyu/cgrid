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
 * 自定义运营商角色数据权限
 *
 * @author things
 */
@Service
public class CustomOperatorRoleSqlServiceImpl extends SqlService {

    @Override
    public String getSql(DataScope dataScope, SysUser user, SysRole role, List<String> scopeCustomIds) {
        StringBuilder sqlString = new StringBuilder();
        String orgAlias = dataScope.orgAlias();
        if (StringUtils.isNotBlank(orgAlias)) {
            sqlString.append(SqlUtils.buildOrgIdSql(orgAlias, user.getOrgId()));
        }
        String userSiteAlias = dataScope.userSiteAlias();
        if (StringUtils.isNotBlank(userSiteAlias)) {
            // 根据用户站点关联过滤
            sqlString.append(SqlUtils.buildUserSiteSql(userSiteAlias, user));
        }
        return sqlString.toString();
    }
}
