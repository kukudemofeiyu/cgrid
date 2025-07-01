package com.things.cgomp.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.enums.DelFlagEnum;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.domain.AppUserGroup;
import com.things.cgomp.system.domain.vo.AppUserGroupVo;
import com.things.cgomp.system.domain.vo.appusergroup.AppUserGroupListGroup;
import com.things.cgomp.system.domain.vo.appusergroup.AppUserGroupUserVo;
import com.things.cgomp.system.dto.AppGroupPageDTO;
import com.things.cgomp.system.mapper.AppUserGroupMapper;
import com.things.cgomp.system.service.IAppUserGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.system.service.IAppUserGroupUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Service
public class AppUserGroupServiceImpl extends ServiceImpl<AppUserGroupMapper, AppUserGroup>
        implements IAppUserGroupService {

    @Resource
    private IAppUserGroupUserService groupUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveGroup(
            AppUserGroup group
    ) {
        group.setCreateBy(SecurityUtils.getUserId())
                .setOperatorId(SecurityUtils.getOperatorId());
        baseMapper.insert(group);

        groupUserService.saveUserIds(
                group.getId(),
                group.getUserIds()
        );

        return group.getId();
    }

    @Override
    public AppUserGroup selectGroup(Long id) {
        AppUserGroup appUserGroup = baseMapper.selectById(id);
        if(appUserGroup == null){
            return null;
        }

        List<Long> userIds = groupUserService.selectUserIds(id);
        return appUserGroup.setUserIds(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editGroup(
            AppUserGroup group
    ) {
        AppUserGroup updateGroup = new AppUserGroup()
                .setId(group.getId())
                .setName(group.getName())
                .setUpdateBy(SecurityUtils.getUserId());
        baseMapper.updateById(updateGroup);

        groupUserService.updateUserIds(
                group.getId(),
                group.getUserIds()
        );
    }

    @Override
    public void deleteGroup(
            Long id
    ) {
        AppUserGroup updateGroup = new AppUserGroup()
                .setId(id)
                .setUpdateBy(SecurityUtils.getUserId())
                .setDelFlag(DelFlagEnum.DELETE.getType());
        baseMapper.updateById(updateGroup);
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public PageInfo<AppUserGroupVo> selectPage(
            AppGroupPageDTO pageDTO
    ) {
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {
            List<AppUserGroupVo> groups = baseMapper.selectGroups(pageDTO);
            return new PageInfo<>(groups);
        }
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public List<AppUserGroupListGroup> selectGroups(
            BaseEntity baseEntity
    ) {
        List<AppUserGroupListGroup> groups = baseMapper.selectPullDownGroups(
                baseEntity
        );

        Map<Long, List<AppUserGroupUserVo>> groupUserMap = selectGroupUserMap(groups);
        groups.forEach(group -> {
            List<AppUserGroupUserVo> users = groupUserMap.get(group.getId());
            group.setUsers(users);
        });

        return groups;
    }

    private Map<Long, List<AppUserGroupUserVo>> selectGroupUserMap(
            List<AppUserGroupListGroup> groups
    ) {
        List<Long> groupIds = groups.stream()
                .map(AppUserGroupListGroup::getId)
                .collect(Collectors.toList());
        return groupUserService.selectGroupUsersMap(groupIds);
    }
}
