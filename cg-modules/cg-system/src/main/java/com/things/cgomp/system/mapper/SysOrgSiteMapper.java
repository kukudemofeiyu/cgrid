package com.things.cgomp.system.mapper;

import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.system.api.domain.SysOrgSite;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author things
 */
@Mapper
public interface SysOrgSiteMapper extends BaseMapperX<SysOrgSite> {

    default int deleteBySiteId(Long siteId){
        LambdaQueryWrapperX<SysOrgSite> wrapper = new LambdaQueryWrapperX<SysOrgSite>()
                .eq(SysOrgSite::getSiteId, siteId);
        return delete(wrapper);
    }

    default int deleteByOrgId(Long orgId){
        LambdaQueryWrapperX<SysOrgSite> wrapper = new LambdaQueryWrapperX<SysOrgSite>()
                .eq(SysOrgSite::getOrgId, orgId);
        return delete(wrapper);
    }

    default int deleteOrgSite(Long orgId, Long siteId){
        LambdaQueryWrapperX<SysOrgSite> wrapper = new LambdaQueryWrapperX<SysOrgSite>()
                .eq(SysOrgSite::getSiteId, siteId)
                .eq(SysOrgSite::getOrgId, orgId);
        return delete(wrapper);
    }
}
