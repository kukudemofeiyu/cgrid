package com.things.cgomp.common.mq.message.callback;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallBackMsg<T > {

    private int code;
    private String errorMsg;
    private T msg;

}
