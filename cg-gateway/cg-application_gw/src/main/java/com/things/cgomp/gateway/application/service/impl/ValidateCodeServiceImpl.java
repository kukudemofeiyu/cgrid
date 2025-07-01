package com.things.cgomp.gateway.application.service.impl;

import com.google.code.kaptcha.Producer;
import com.things.cgomp.common.core.constant.CacheConstants;
import com.things.cgomp.common.core.constant.Constants;
import com.things.cgomp.common.core.exception.CaptchaException;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.utils.sign.Base64;
import com.things.cgomp.common.core.utils.uuid.IdUtils;
import com.things.cgomp.common.core.web.domain.AjaxResult;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.gateway.application.config.properties.CaptchaProperties;
import com.things.cgomp.gateway.application.enums.ErrorCodeConstants;
import com.things.cgomp.gateway.application.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 验证码实现处理
 *
 * @author things
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CaptchaProperties captchaProperties;

    /**
     * 生成验证码
     */
    @Override
    public AjaxResult createCaptcha() throws IOException, CaptchaException
    {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = captchaProperties.getEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled)
        {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        String captchaType = captchaProperties.getType();
        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisService.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    /**
     * 校验验证码
     */
    @Override
    public void checkCaptcha(String code, String uuid) throws CaptchaException
    {
        if (StringUtils.isEmpty(code))
        {
            throw exception(ErrorCodeConstants.CAPTCHA_CODE_NOT_NULL);
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisService.getCacheObject(verifyKey);
        if (captcha == null)
        {
            throw exception(ErrorCodeConstants.CAPTCHA_CODE_IS_EXPIRED);
        }
        redisService.deleteObject(verifyKey);
        if (!code.equalsIgnoreCase(captcha))
        {
            throw exception(ErrorCodeConstants.CAPTCHA_CODE_ERROR);
        }
    }
}
