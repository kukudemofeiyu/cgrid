package com.things.cgomp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUserBlacklist;

/**
 * 注册用户黑名单服务类
 */
public interface IAppUserBlacklistService extends IService<AppUserBlacklist> {

    /**
     * 查询分页
     * @param blacklist 分页请求对象
     * @return PageInfo
     */
    PageInfo<AppUserBlacklist> selectPage(AppUserBlacklist blacklist);

    /**
     * 根据ID查询记录
     * @param id ID
     * @return AppUserBlacklist
     */
    AppUserBlacklist selectBlackListById(Long id);

    /**
     * 新增黑名单
     * @param blacklist 新增对象
     * @return Integer
     */
    Integer saveBlacklist(AppUserBlacklist blacklist);

    /**
     * 修改黑名单
     * @param blacklist 新增对象
     * @return Integer
     */
    Integer updateBlacklist(AppUserBlacklist blacklist);
}
