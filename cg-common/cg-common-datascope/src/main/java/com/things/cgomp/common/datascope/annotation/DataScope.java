package com.things.cgomp.common.datascope.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限过滤注解
 * 
 * @author things
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope
{
    /**
     * 组织表的别名
     */
    String orgAlias() default "";

    /**
     * 用户表的别名
     */
    String userAlias() default "";

    /**
     * 用户站点关联表的别名
     */
    String userSiteAlias() default "";

    /**
     * 用户运营商关联表的别名
     */
    String userOperatorAlias() default "";

    /**
     * 权限字符（用于多个角色匹配符合要求的权限）默认根据权限注解@RequiresPermissions获取，多个权限用逗号分隔开来
     */
    String permission() default "";
}
