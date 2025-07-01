package com.things.cgomp.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.system.domain.AppUserGroupUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * app用户组映射表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface AppUserGroupUserMapper extends BaseMapper<AppUserGroupUser> {

    List<Long> selectUserIds(
           @Param("groupId") Long groupId
    );

    Integer deleteByGroupId(
            @Param("groupId") Long groupId
    );

    List<AppUserGroupUser> selectGroupUsers(
            @Param("groupIds") List<Long> groupIds
    );

}
