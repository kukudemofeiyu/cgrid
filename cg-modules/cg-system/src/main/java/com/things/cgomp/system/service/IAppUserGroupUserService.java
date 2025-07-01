package com.things.cgomp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.system.domain.AppUserGroupUser;
import com.things.cgomp.system.domain.vo.appusergroup.AppUserGroupUserVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * app用户组映射表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface IAppUserGroupUserService extends IService<AppUserGroupUser> {

    void saveUserIds(
            Long groupId,
            List<Long> userIds
    );

    List<Long> selectUserIds(
            Long groupId
    );

    void updateUserIds(
            Long groupId,
            List<Long> userIds
    );

    Map<Long, List<AppUserGroupUserVo>> selectGroupUsersMap(
            List<Long> groupIds
    );

}
