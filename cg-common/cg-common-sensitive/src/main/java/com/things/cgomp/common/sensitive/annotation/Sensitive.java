package com.things.cgomp.common.sensitive.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.things.cgomp.common.sensitive.config.SensitiveJsonSerializer;
import com.things.cgomp.common.sensitive.enums.DesensitizedType;

/**
 * 数据脱敏注解
 *
 * @author things
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveJsonSerializer.class)
public @interface Sensitive
{
    DesensitizedType desensitizedType();
}
