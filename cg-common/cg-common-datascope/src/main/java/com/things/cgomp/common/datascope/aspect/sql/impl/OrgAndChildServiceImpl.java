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
 * 组织及以下数据权限
 * @author things
 */
@Service
public class OrgAndChildServiceImpl extends SqlService {

    @Override
    public String getSql(DataScope dataScope, SysUser user, SysRole role, List<String> scopeCustomIds) {
        StringBuilder sqlString = new StringBuilder();
        String orgAlias = dataScope.orgAlias();
        if(StringUtils.isNotBlank(orgAlias)) {
            sqlString.append(SqlUtils.buildOrgAndChildIdSql(orgAlias, user.getOrgId()));
        }
        return sqlString.toString();
    }
}
