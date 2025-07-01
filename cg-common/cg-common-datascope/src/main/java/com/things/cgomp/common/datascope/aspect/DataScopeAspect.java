package com.things.cgomp.common.datascope.aspect;

import com.things.cgomp.common.core.constant.UserConstants;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.datascope.aspect.sql.SqlService;
import com.things.cgomp.common.datascope.aspect.sql.SqlServiceFactory;
import com.things.cgomp.common.datascope.enums.DataScopeEnum;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.api.model.LoginUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.things.cgomp.common.datascope.constants.DataScopeConstant.DATA_SCOPE;
import static com.things.cgomp.common.datascope.enums.DataScopeEnum.ALL;

/**
 * 数据过滤处理
 *
 * @author things
 */
@Aspect
@Component
public class DataScopeAspect
{
    @Resource
    private SqlServiceFactory sqlServiceFactory;

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScope controllerDataScope) throws Throwable
    {
        clearDataScope(point);
        handleDataScope(point, controllerDataScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, DataScope controllerDataScope)
    {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getSysUser();
            // 如果是超级管理员，则不过滤数据
            if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin())
            {
                Map<String, Object> params = getParams(joinPoint);
                if( params == null)
                {
                    return;
                }
                String sql = dataScopeFilter(controllerDataScope, currentUser);
                if (StringUtils.isNotBlank(sql))
                {
                    params.put(DATA_SCOPE, " AND (" + sql.substring(4) + ")");
                }
            }
        }
    }

    /**
     * 数据范围过滤
     * @param user 用户
     */
    public String dataScopeFilter(DataScope dataScopeAnnotation, SysUser user)
    {
        StringBuilder sqlString = new StringBuilder();
        List<String> conditions = new ArrayList<>();
        List<String> scopeCustomIds = getScopeCustomIds(dataScopeAnnotation.permission(), user.getRoles());

        for (SysRole role : user.getRoles())
        {
            String dataScope = role.getDataScope();
            if (conditions.contains(dataScope) || StringUtils.equals(role.getStatus(), UserConstants.ROLE_DISABLE))
            {
                continue;
            }
            if (ALL.getCode().equals(dataScope))
            {
                sqlString = new StringBuilder();
                conditions.add(dataScope);
                break;
            }
            DataScopeEnum dataScopeEnum = DataScopeEnum.getDataScopeEnum(dataScope);

            SqlService sqlService = sqlServiceFactory.getSqlService(dataScopeEnum);
            if(sqlService == null){
                continue;
            }
            sqlString.append(sqlService.getSql(dataScopeAnnotation, user ,role, scopeCustomIds));
            conditions.add(dataScope);
        }

        // 角色都不包含传递过来的权限字符，这个时候sqlString也会为空，所以要限制一下,不查询任何数据
        if (StringUtils.isEmpty(conditions))
        {
            sqlString.append(StringUtils.format(" OR 1 != 1 "));
        }

        return sqlString.toString();
    }

    private List<String> getScopeCustomIds(String checkPermission, List<SysRole> roles) {
        List<String> scopeCustomIds = new ArrayList<>();
        /*String permission = StringUtils.defaultIfEmpty(checkPermission, SecurityContextHolder.getPermission());
        roles.forEach(role->{
            if (CUSTOM.getCode().equals(role.getDataScope()) && StringUtils.equals(role.getStatus(), UserConstants.ROLE_NORMAL) && StringUtils.containsAny(role.getPermissions(), Convert.toStrArray(permission)))
            {
                scopeCustomIds.add(Convert.toStr(role.getRoleId()));
            }
        });*/
        return scopeCustomIds;
    }

    private static Map<String, Object> getParams(JoinPoint joinPoint) {
        if (joinPoint.getArgs().length == 0) {
            return null;
        }
        Object params = joinPoint.getArgs()[0];
        if (!(params instanceof BaseEntity)) {
            return null;
        }
        BaseEntity baseEntity = (BaseEntity) params;
        return baseEntity.getParams();
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint)
    {
        Map<String, Object> params = getParams(joinPoint);
        if (params == null) {
            return;
        }
        params.put(DATA_SCOPE, "");
    }
}
