package com.things.cgomp.common.log.annotation;

import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * 
 * @author things
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能类型
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 功能方法
     */
    public String method() default "";

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     */
    public String[] excludeParamNames() default {};
}
