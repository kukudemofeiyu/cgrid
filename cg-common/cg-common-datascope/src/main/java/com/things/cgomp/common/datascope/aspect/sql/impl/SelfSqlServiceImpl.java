package com.things.cgomp.common.datascope.aspect.sql.impl;

import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.datascope.aspect.sql.SqlService;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.domain.SysUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 仅本人数据权限
 *
 * @author things
 */
@Service
public class SelfSqlServiceImpl extends SqlService {

    @Override
    public String getSql(DataScope dataScope, SysUser user, SysRole role, List<String> scopeCustomIds) {
        StringBuilder sqlString = new StringBuilder();
        String userAlias = dataScope.userAlias();
        if (StringUtils.isNotEmpty(userAlias)) {
            sqlString.append(StringUtils.format(" OR {}.user_id = {} ",
                    userAlias,
                    user.getUserId()));
        } else {
            // 数据权限为仅本人且没有userAlias别名不查询任何数据
            String orgAlias = dataScope.orgAlias();
            if (StringUtils.isNotEmpty(orgAlias)) {
                sqlString.append(StringUtils.format(" OR {}.org_id = -1 ", orgAlias));
            }
        }
        return sqlString.toString();
    }
}
