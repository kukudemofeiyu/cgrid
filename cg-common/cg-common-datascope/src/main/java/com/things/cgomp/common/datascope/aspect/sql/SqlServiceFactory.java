package com.things.cgomp.common.datascope.aspect.sql;

import com.things.cgomp.common.datascope.aspect.sql.impl.*;
import com.things.cgomp.common.datascope.enums.DataScopeEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author things
 */
@Component
public class SqlServiceFactory {

    @Resource
    private ApplicationContext applicationContext;

    public SqlService getSqlService(DataScopeEnum dataScope) {
        if(dataScope== null){
            return null;
        }

        SqlService sqlService = null;
        switch (dataScope) {
            case CUSTOM:
                sqlService = applicationContext.getBean(CustomSqlServiceImpl.class);
                break;
            case ORG:
                sqlService = applicationContext.getBean(OrgSqlServiceImpl.class);
                break;
            case ORG_AND_CHILD:
                sqlService = applicationContext.getBean(OrgAndChildServiceImpl.class);
                break;
            case SELF:
                sqlService = applicationContext.getBean(SelfSqlServiceImpl.class);
                break;
            case CUSTOM_PLATFORM_ROLE:
                sqlService = applicationContext.getBean(CustomPlatformRoleSqlServiceImpl.class);
                break;
            case CUSTOM_OPERATOR_ROLE:
                sqlService = applicationContext.getBean(CustomOperatorRoleSqlServiceImpl.class);
                break;
            default:
                break;
        }

        return sqlService;
    }
}
