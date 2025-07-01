package com.things.cgomp.common.datascope.aspect.sql;

import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.domain.SysUser;

import java.util.List;

/**
 * @author things
 */
public abstract class SqlService {

    protected void appSql(StringBuilder sql, String subSql) {
        if(sql.length() == 0 ){
            sql.append(" OR");
        }else {
            sql.append(" and");
        }
        sql.append(subSql);
    }

    public abstract String getSql(DataScope dataScope, SysUser user, SysRole role, List<String> scopeCustomIds);
}
