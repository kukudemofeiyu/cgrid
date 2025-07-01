package com.things.cgomp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppUserBlacklist;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.mapper.AppUserBlacklistMapper;
import com.things.cgomp.system.service.IAppUserBlacklistService;
import com.things.cgomp.system.service.IAppUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * 注册用户黑名单实现类
 */
@Service
public class AppUserBlacklistServiceImpl extends ServiceImpl<AppUserBlacklistMapper, AppUserBlacklist> implements IAppUserBlacklistService {

    @Resource
    private IAppUserService appUserService;

    @Override
    public PageInfo<AppUserBlacklist> selectPage(AppUserBlacklist blacklist) {
        startPage();
        List<AppUserBlacklist> list = baseMapper.selectBlackList(blacklist);
        return new PageInfo<>(list);
    }

    @Override
    public AppUserBlacklist selectBlackListById(Long id) {
        return baseMapper.selectBlackListById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveBlacklist(AppUserBlacklist blacklist) {
        AppUser appUser = appUserService.selectUserByMobile(blacklist.getMobile());
        if (appUser == null) {
            throw exception(ErrorCodeConstants.APP_USER_MOBILE_NOT_EXIST);
        }
        AppUserBlacklist checkBlacklist = baseMapper.checkUserUnique(appUser.getUserId());
        if (checkBlacklist != null && checkBlacklist.getUnsealTime().after(new Date())) {
            // 存在未解禁的数据
            throw exception(ErrorCodeConstants.APP_USER_BLACKLIST_EXIST, blacklist.getMobile());
        }
        if (blacklist.getSiteRange() == null) {
            // 默认黑名单维度为全部站点
            blacklist.setSiteRange(1);
        }
        Date date = new Date();
        blacklist.setUserId(appUser.getUserId());
        blacklist.setCreateBy(SecurityUtils.getUserId());
        blacklist.setUpdateBy(SecurityUtils.getUserId());
        blacklist.setCreateTime(date);
        blacklist.setUpdateTime(date);
        // 新增黑名单
        return baseMapper.insert(blacklist);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateBlacklist(AppUserBlacklist blacklist) {
        return baseMapper.updateById(blacklist);
    }
}
