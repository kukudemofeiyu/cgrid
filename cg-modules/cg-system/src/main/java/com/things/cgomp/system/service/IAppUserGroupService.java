package com.things.cgomp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.system.domain.AppUserGroup;
import com.things.cgomp.system.domain.vo.AppUserGroupVo;
import com.things.cgomp.system.domain.vo.appusergroup.AppUserGroupListGroup;
import com.things.cgomp.system.dto.AppGroupPageDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface IAppUserGroupService extends IService<AppUserGroup> {

    Long saveGroup(
            AppUserGroup group
    );

    AppUserGroup selectGroup(
            Long id
    );

    void editGroup(
            AppUserGroup group
    );

    void deleteGroup(
            Long id
    );

    PageInfo<AppUserGroupVo> selectPage(
            AppGroupPageDTO pageDTO
    );

    List<AppUserGroupListGroup> selectGroups(
            BaseEntity baseEntity
    );

}
