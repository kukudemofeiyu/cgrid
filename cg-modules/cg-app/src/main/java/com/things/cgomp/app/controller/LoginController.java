package com.things.cgomp.app.controller;

import com.things.cgomp.app.domain.vo.AppLoginVO;
import com.things.cgomp.app.service.AppUserService;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.security.service.TokenService;
import com.things.cgomp.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AppUserService appUserService;

    /**
     * 普通登录
     *
     * @param code
     * @return
     */
    @GetMapping("/appletWeChatLogin")
    public R<AppLoginVO> appletWeChatLogin(@RequestParam("code") String code) {
        //用户登录(普通登录)
        AppLoginVO appLoginVO = appUserService.appletWeChatLogin(code);
        return R.ok(appLoginVO);
    }

    /**
     * 手机号登录
     *
     * @param code
     * @param phoneCode
     * @return
     */
    @GetMapping("/phoneLogin")
    public R<AppLoginVO> appletWeChatLogin(@RequestParam("code") String code, @RequestParam("phoneCode") String phoneCode) {
        //手机号登录
        AppLoginVO appLoginVO = appUserService.phoneLogin(code, phoneCode);
        return R.ok(appLoginVO);
    }

    @PostMapping(value = "logout", name = "退出登录")
    public R<?> logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            // 删除用户缓存记录
            tokenService.delLoginUser(token);
        }
        return R.ok();
    }

    /**
     * 获取小程序Token接口
     *
     * @return
     */
    @GetMapping("/getToken")
    public R<String> getToken(@RequestParam("userId") String userId) {
        String token = appUserService.getToken(userId);
        return R.ok(token);
    }
}
