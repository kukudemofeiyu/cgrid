package com.things.cgomp.system.mapper;

import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.system.api.domain.SysUserSite;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author things
 */
@Mapper
public interface SysUserSiteMapper extends BaseMapperX<SysUserSite> {

    default List<Long> selectSiteIds(Long userId){
        List<SysUserSite> list = selectList(SysUserSite::getUserId, userId);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(SysUserSite::getSiteId).collect(Collectors.toList());
    }

    default int deleteByUserId(Long userId){
        LambdaQueryWrapperX<SysUserSite> wrapper = new LambdaQueryWrapperX<SysUserSite>()
                .eq(SysUserSite::getUserId, userId);
        return delete(wrapper);
    }

    default int deleteBySiteId(Long siteId){
        LambdaQueryWrapperX<SysUserSite> wrapper = new LambdaQueryWrapperX<SysUserSite>()
                .eq(SysUserSite::getSiteId, siteId);
        return delete(wrapper);
    }

    /**
     * 通过用户ID删除用户和站点关联
     *
     * @param userIds 用户ID集合
     * @return 结果
     */
    int deleteUserSite(Long[] userIds);
}
