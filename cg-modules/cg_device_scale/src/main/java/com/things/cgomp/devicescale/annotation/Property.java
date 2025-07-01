package com.things.cgomp.devicescale.annotation;


import com.things.cgomp.devicescale.message.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    int index() default -1;


    int length() default -1;

    /**
     * 该长度依赖于哪个属性
     *
     * @return
     */
    String lengthName() default "";

    DataType type() default DataType.BYTE;

    String desc() default "";

}