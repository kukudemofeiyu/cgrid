package com.things.cgomp.devicescale.message;

import com.things.cgomp.devicescale.mapping.Handler;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public abstract class AbstractMessage<T extends AbstractBody> {


    protected Handler handler;


    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public abstract int getCmd();

    public abstract int getSeq();


    public abstract String getTs();

    public abstract String getVersion();

    public abstract boolean getEncryptionType();


    protected T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


    public abstract Integer getHeaderLength();
}