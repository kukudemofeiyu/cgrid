package com.things.cgomp.system.controller;


import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.system.api.domain.AppUserTrendDateData;
import com.things.cgomp.system.api.dto.AppUserTrendDataDTO;
import com.things.cgomp.system.convert.AppUserConvert;
import com.things.cgomp.system.domain.dto.RegisterTransferDTO;
import com.things.cgomp.system.domain.vo.AppUserSimpleVO;
import com.things.cgomp.system.service.IAppUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 注册用户管理
 *
 * @author things
 */
@Log(title = "注册用户管理")
@RestController
@RequestMapping("/register")
public class AppUserController {

    @Resource
    private IAppUserService appUserService;

    /**
     * 获取注册用户分页列表
     */
    @RequiresPermissions("app:register:list")
    @GetMapping("/page")
    public R<PageInfo<AppUser>> list(AppUser user) {
        PageInfo<AppUser> pageInfo = appUserService.selectPage(user);
        return R.ok(pageInfo);
    }

    /**
     * 获取注册用户简约列表
     * 下拉框
     */
    @GetMapping("/simple-list")
    public R<List<AppUserSimpleVO>> simpleList(AppUser user){
        user.setStatus(CommonStatus.OK.getCode());
        List<AppUser> list = appUserService.selectList(user);
        return R.ok(AppUserConvert.INSTANCE.convertList(list));
    }

    @GetMapping(name = "查询用户信息")
    public R<AppUser> selectUser(
            @RequestParam(value = "userId") Long userId
    ) {
        AppUser user = appUserService.getById(userId);
        return R.ok(user);
    }

    @PutMapping(value = "/firstChargeStatus", name = "更新用户首次充电状态")
    R<?> updateFirstChargeStatus(
            Long userId
    ) {
        appUserService.updateFirstChargeStatus(userId);
        return R.ok();
    }

    @GetMapping(value = "/firstChargeStatus", name = "查询用户首次充电状态")
    R<Integer> selectFirstChargeStatus(
            Long userId
    ) {
        Integer firstChargeStatus = appUserService.selectFirstChargeStatus(userId);
        return R.ok(firstChargeStatus);
    }

    /**
     * TODO、暂时挂起，待后续IC卡功能
     * 注册用户转账
     */
    @RequiresPermissions("app:register:transfer")
    //@PostMapping("/transfer")
    public R<Boolean> transfer(@Validated @RequestBody RegisterTransferDTO req){
        return R.ok(appUserService.transferFromCard(req));
    }

    @InnerAuth
    @GetMapping(value = "/getTotalTrend", name = "获取注册用户累计趋势")
    public R<AppUserTrendDataDTO> getUserCount(@RequestParam(value = "beginDate", required = false) String beginDate,
                                               @RequestParam(value = "endDate", required = false) String endDate){
        long totalCount = appUserService.count();
        List<AppUserTrendDateData> trendDataList = appUserService.selectTrendDateData(beginDate, endDate);
        AppUserTrendDataDTO resp = AppUserTrendDataDTO.builder()
                .totalCount(totalCount)
                .dateTrends(trendDataList)
                .build();
        return R.ok(resp);
    }
}
