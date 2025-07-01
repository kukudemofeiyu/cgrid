package com.things.cgomp.gateway.application.service;

import com.things.cgomp.common.core.exception.CaptchaException;
import com.things.cgomp.common.core.web.domain.AjaxResult;

import java.io.IOException;

/**
 * 验证码处理
 *
 * @author things
 */
public interface ValidateCodeService
{
    /**
     * 生成验证码
     */
    public AjaxResult createCaptcha() throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    public void checkCaptcha(String key, String value) throws CaptchaException;
}
