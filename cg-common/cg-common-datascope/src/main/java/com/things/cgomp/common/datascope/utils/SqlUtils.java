package com.things.cgomp.common.datascope.utils;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.system.api.domain.SysUser;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author things
 */
public class SqlUtils {

    public static <T> String listToStr(List<T> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return "0";
        }
        return StringUtils.join(ids, ",");
    }

    public static <T> String setToStr(Set<T> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return "0";
        }
        return StringUtils.join(ids, ",");
    }

    /**
     * 构建单个组织ID过滤sql
     * @param orgAlias 组织表别名
     * @param orgId    要过滤的组织ID
     * @return sql
     */
    public static String buildOrgIdSql(String orgAlias, Long orgId){
        Set<Long> orgIds = new HashSet<>();
        // 默认添加组织ID为0的数据，表示所有组织公用的数据
        orgIds.add(SecurityConstants.ROOT_ORG_ID);
        orgIds.add(orgId);
        String orgIdStr = setToStr(orgIds);
        return StringUtils.format(" OR {}.org_id in ({}) ", orgAlias, orgIdStr);
    }

    /**
     * 构建组织及组织下级数据权限的sql
     * @param orgAlias  组织表别名
     * @param orgId     顶级节点组组织ID
     * @return sql
     */
    public static String buildOrgAndChildIdSql(String orgAlias, Long orgId){
        Set<Long> orgIds = new HashSet<>();
        // 默认添加组织ID为0的数据，表示所有组织公用的数据
        orgIds.add(SecurityConstants.ROOT_ORG_ID);
        orgIds.add(orgId);
        String orgIdStr = setToStr(orgIds);
        return StringUtils.format(" OR {}.org_id IN ( SELECT org_id FROM system_org WHERE org_id in ({}) or find_in_set( {} , ancestors ))",
                orgAlias,
                orgIdStr,
                orgId);
    }

    /**
     * 构建用户站点关联sql
     * @param userSiteAlias  用户站点关联表
     * @param user 用户对象
     * @return sql
     */
    public static String buildUserSiteSql(String userSiteAlias, SysUser user){
        Integer siteRange = user.getSiteRange();
        if (SecurityConstants.DATA_SCOPE_RANGE_ALL.equals(siteRange)) {
            // 全部站点权限
            return StringUtils.EMPTY;
        }
        return StringUtils.format(" AND {}.user_id = {} ",
                userSiteAlias,
                user.getUserId());
    }

    /**
     * 构建用户运营商关联sql
     * @param userOperatorAlias 用户运营商关联表
     * @param user  用户对象
     * @return sql
     */
    public static String buildUserOperatorSql(String userOperatorAlias, SysUser user) {
        Integer operatorRange = user.getOperatorRange();
        if (SecurityConstants.DATA_SCOPE_RANGE_ALL.equals(operatorRange)) {
            // 全部运营商权限
            return StringUtils.EMPTY;
        }
        return StringUtils.format(" OR {}.user_id = {} ",
                userOperatorAlias,
                user.getUserId());
    }
}
