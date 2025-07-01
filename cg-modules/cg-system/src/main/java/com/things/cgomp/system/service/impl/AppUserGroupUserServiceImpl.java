package com.things.cgomp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.system.domain.AppUserGroupUser;
import com.things.cgomp.system.domain.vo.appusergroup.AppUserGroupUserVo;
import com.things.cgomp.system.mapper.AppUserGroupUserMapper;
import com.things.cgomp.system.service.IAppUserGroupUserService;
import com.things.cgomp.system.service.IAppUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * app用户组映射表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Service
public class AppUserGroupUserServiceImpl extends ServiceImpl<AppUserGroupUserMapper, AppUserGroupUser>
        implements IAppUserGroupUserService {

    @Resource
    private IAppUserService appUserService;

    @Override
    public void saveUserIds(
            Long groupId,
            List<Long> userIds
    ) {
        if(CollectionUtils.isEmpty(userIds)){
            return;
        }

        userIds.stream()
                .map(userId -> new AppUserGroupUser(
                        groupId,
                        userId
                ))
                .forEach(baseMapper::insert);

    }

    @Override
    public List<Long> selectUserIds(
            Long groupId
    ) {
        return baseMapper.selectUserIds(
                groupId
        );
    }

    @Override
    public void updateUserIds(
            Long groupId,
            List<Long> userIds
    ) {
        baseMapper.deleteByGroupId(
                groupId
        );

        saveUserIds(
                groupId,
                userIds
        );
    }

    @Override
    public Map<Long, List<AppUserGroupUserVo>> selectGroupUsersMap(
            List<Long> groupIds
    ) {
        if(CollectionUtils.isEmpty(groupIds)){
            return new HashMap<>();
        }

        List<AppUserGroupUserVo> groupUserVos = getGroupUserVos(
                groupIds
        );

        Map<Long, List<AppUserGroupUserVo>> groupUsersMap = new HashMap<>();

        for (AppUserGroupUserVo groupUserVo : groupUserVos) {
            List<AppUserGroupUserVo> subGroupUserVos =
                    groupUsersMap.computeIfAbsent(groupUserVo.getGroupId(), k -> new ArrayList<>());
            subGroupUserVos.add(groupUserVo);
        }

        return groupUsersMap;
    }

    private List<AppUserGroupUserVo> getGroupUserVos(
            List<Long> groupIds
    ) {
        List<AppUserGroupUser> groupUsers = baseMapper.selectGroupUsers(groupIds);

        Map<Long, AppUser> userMap = selectUserMap(groupUsers);
        return groupUsers.stream()
                .map(groupUser -> getGroupUserVo(
                        userMap,
                        groupUser
                ))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Map<Long, AppUser> selectUserMap(List<AppUserGroupUser> groupUsers) {
        List<Long> userIds = groupUsers.stream()
                .map(AppUserGroupUser::getUserId)
                .distinct()
                .collect(Collectors.toList());
        return appUserService.selectUserMap(userIds);
    }

    private AppUserGroupUserVo getGroupUserVo(
            Map<Long, AppUser> userMap,
            AppUserGroupUser groupUser
    ) {
        AppUser user = userMap.get(groupUser.getUserId());
        if(user == null){
            return null;
        }
        return new AppUserGroupUserVo()
                .setGroupId(groupUser.getGroupId())
                .setUserId(user.getUserId())
                .setMobile(user.getMobile())
                .setNickName(user.getNickName());
    }
}
