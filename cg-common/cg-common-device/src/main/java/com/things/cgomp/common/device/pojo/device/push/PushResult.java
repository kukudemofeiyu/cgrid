package com.things.cgomp.common.device.pojo.device.push;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
public class PushResult<T> implements Serializable {

    private Boolean succeed;

    private String requestId;

    private Integer code;

    private String codeMsg;


    private T data;
    public T getData() {
        return data;
    }

    public static  PushResult success(){
        return PushResult.builder().succeed(true).code(200).build();
    }

    public static  <T> PushResult success(T data){
        return PushResult.builder().succeed(true).code(200).data(data).build();
    }

    public static  <T> PushResult fail(Integer code, String errorMsg){
        return PushResult.builder().succeed(false).code(code).codeMsg(errorMsg).build();
    }

    public static  <T> PushResult fail(String errorMsg){
        return PushResult.builder().succeed(false).code(500).codeMsg(errorMsg).build();
    }

    public static  <T> PushResult fail(Integer code, String errorMsg, T data){
        return PushResult.builder().succeed(false).code(code).codeMsg(errorMsg).data(data).build();
    }




}
