package com.things.cgomp.devicescale.annotation;


import com.things.cgomp.devicescale.message.DataType;

import java.lang.annotation.*;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapping {

    int[] types();

    String desc() default "";
}