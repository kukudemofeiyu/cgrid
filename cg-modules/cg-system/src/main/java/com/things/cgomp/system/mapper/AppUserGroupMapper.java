package com.things.cgomp.system.mapper;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.system.domain.AppUserGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.system.domain.vo.AppUserGroupVo;
import com.things.cgomp.system.domain.vo.appusergroup.AppUserGroupListGroup;
import com.things.cgomp.system.dto.AppGroupPageDTO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface AppUserGroupMapper extends BaseMapper<AppUserGroup> {

    List<AppUserGroupVo> selectGroups(
            AppGroupPageDTO pageDTO
    );

    List<AppUserGroupListGroup> selectPullDownGroups(
            BaseEntity baseEntity
    );
}
