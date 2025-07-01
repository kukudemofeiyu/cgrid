package com.things.cgomp.devicescale.message;


import com.things.cgomp.devicescale.mapping.Handler;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.nio.ByteBuffer;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public abstract class AbstractBody {


    public AbstractBody() {
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
