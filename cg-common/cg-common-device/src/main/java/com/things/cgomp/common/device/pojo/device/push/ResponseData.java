package com.things.cgomp.common.device.pojo.device.push;


import java.io.Serializable;

/**
 * 响应信息主体
 *
 * 
 */
public class ResponseData<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = 200;

    /** 失败 */
    public static final int FAIL = 500;

    private int code;

    private String msg;

    private T data;

    public static ResponseData<Boolean> ok()
    {
        return restResult(Boolean.TRUE, SUCCESS, null);
    }

    public static <T> ResponseData<T> ok(T data)
    {
        return restResult(data, SUCCESS, null);
    }

    public static <T> ResponseData<T> ok(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }
    public static <T> ResponseData<T> StsOk(T data)
    {
        return restResult(data, SUCCESS,null);
    }

    public static <T> ResponseData<T> fail()
    {
        return restResult(null, FAIL, null);
    }

    public static <T> ResponseData<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public static <T> ResponseData<T> fail(T data)
    {
        return restResult(data, FAIL, null);
    }

    public static <T> ResponseData<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public static <T> ResponseData<T> fail(int code, String msg)
    {
        return restResult(null, code, msg);
    }

    private static <T> ResponseData<T> restResult(T data, int code, String msg)
    {
        ResponseData<T> apiResult = new ResponseData<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public static <T> Boolean isError(ResponseData<T> ret)
    {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(ResponseData<T> ret)
    {
        return ResponseData.SUCCESS == ret.getCode();
    }

    public <T>  Boolean success() {
        return ResponseData.SUCCESS == getCode();
    }


}
