package com.things.cgomp.system.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.utils.file.FileTypeUtils;
import com.things.cgomp.common.core.utils.file.MimeTypeUtils;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.core.web.domain.AjaxResult;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.service.TokenService;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.file.api.RemoteFileService;
import com.things.cgomp.file.api.domain.FileResp;
import com.things.cgomp.file.api.domain.FileUploadReq;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.api.model.LoginUser;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 个人信息 业务处理
 * 
 * @author things
 */
@Log(title = "个人信息")
@RestController
@RequestMapping("/user/profile")
public class SysProfileController extends BaseController
{
    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private TokenService tokenService;
    
    @Resource
    private RemoteFileService remoteFileService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile()
    {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(username));
        ajax.put("postGroup", userService.selectUserPostGroup(username));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(method = "修改基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser currentUser = loginUser.getSysUser();
        user.setUsername(currentUser.getUsername());
        user.setUserId(currentUser.getUserId());
        if (StringUtils.isNotEmpty(user.getMobile()) && !userService.checkPhoneUnique(user))
        {
            throw exception(ErrorCodeConstants.USER_MOBILE_IS_EXIST, user.getMobile());
        }
        if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
        {
            throw exception(ErrorCodeConstants.USER_EMAIL_IS_EXIST, user.getEmail());
        }
        user.setPassword(null);
        user.setOrgId(null);
        if (userService.updateUserProfile(user))
        {
            // 更新缓存用户信息
            updateUserCache(user, loginUser);
            return success();
        }
        throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
    }

    private void updateUserCache(SysUser user, LoginUser loginUser) {
        SysUser sysUser = loginUser.getSysUser();
        // 更新缓存用户信息
        String mobile = user.getMobile();
        if (StringUtils.isNotBlank(mobile)) {
            sysUser.setMobile(mobile);
        }

        String email = user.getEmail();
        if (StringUtils.isNotBlank(email)) {
            sysUser.setEmail(email);
        }
        tokenService.setLoginUser(loginUser);
    }

    /**
     * 重置密码
     */
    @Log(method = "重置密码", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String username = loginUser.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        String password = user.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password))
        {
            throw exception(ErrorCodeConstants.USER_OLD_PASSWORD_NOT_RIGHT);
        }
        if (SecurityUtils.matchesPassword(newPassword, password))
        {
            throw exception(ErrorCodeConstants.USER_NEW_PASSWORD_CANNOT_BE_SAME_OLD_PASSWORD);
        }
        newPassword = SecurityUtils.encryptPassword(newPassword);
        if (userService.resetUserPwd(username, newPassword) > 0)
        {
            // 清除缓存
            tokenService.removeToken(user.getUserId());
            return success();
        }
        throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * 头像上传
     */
    @Log(method = "头像上传", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file)
    {
        if (file.isEmpty()) {
            throw exception(ErrorCodeConstants.USER_AVATAR_NOT_NULL);
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String extension = FileTypeUtils.getExtension(file);
        if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION))
        {
            throw exception(ErrorCodeConstants.USER_AVATAR_FILE_FORMAT_BE_INCORRECT);
        }
        FileUploadReq uploadReq = new FileUploadReq().setFile(file);
        R<FileResp> respR = remoteFileService.upload(uploadReq);
        if (StringUtils.isNull(respR) || StringUtils.isNull(respR.getData()))
        {
            throw exception(ErrorCodeConstants.USER_AVATAR_UPLOAD_FAIL);
        }
        String url = respR.getData().getUrl();
        if (userService.updateUserAvatar(loginUser.getUsername(), url))
        {
            AjaxResult ajax = AjaxResult.success();
            Map<String,Object> data = new HashMap<>();
            data.put("imgUrl", url);
            ajax.put("data", data);
            // 更新缓存用户头像
            loginUser.getSysUser().setAvatar(url);
            tokenService.setLoginUser(loginUser);
            return ajax;
        }
        throw exception(ErrorCodeConstants.USER_AVATAR_UPLOAD_FAIL);
    }
}
