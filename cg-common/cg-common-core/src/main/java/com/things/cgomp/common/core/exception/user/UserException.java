package com.things.cgomp.common.core.exception.user;

import com.things.cgomp.common.core.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author things
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
