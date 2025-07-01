package com.things.cgomp.system.service.impl;

import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.system.api.domain.SysOrgSite;
import com.things.cgomp.system.api.domain.SysSiteEf;
import com.things.cgomp.system.api.domain.SysUserSite;
import com.things.cgomp.system.mapper.SysOrgSiteMapper;
import com.things.cgomp.system.mapper.SysUserSiteMapper;
import com.things.cgomp.system.service.ISysSiteEfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author things
 */
@Service
public class SysSiteEfServiceImpl implements ISysSiteEfService {

    @Resource
    private SysUserSiteMapper userSiteMapper;
    @Resource
    private SysOrgSiteMapper orgSiteMapper;

    @Override
    public List<Long> selectSiteIdsByUserId(Long userId) {
        LambdaQueryWrapperX<SysUserSite> wrapper = new LambdaQueryWrapperX<SysUserSite>()
                .eq(SysUserSite::getUserId, userId);
        List<SysUserSite> list = userSiteMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(SysUserSite::getSiteId).distinct().collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addSiteEf(SysSiteEf siteEf) {
        insertSiteEf(siteEf.getOrgSites(), siteEf.getUserSites());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSiteEf(SysSiteEf siteEf) {
        SysOrgSite removeOrgSite = siteEf.getRemoveOrgSite();
        if (removeOrgSite != null && removeOrgSite.getOrgId() != null && removeOrgSite.getSiteId() != null) {
            // 删除原来的关联关系
            orgSiteMapper.deleteOrgSite(removeOrgSite.getOrgId(), removeOrgSite.getSiteId());
        }
        insertSiteEf(siteEf.getOrgSites(), siteEf.getUserSites());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSiteEfBySite(Long siteId) {
        if (siteId == null) {
            return 0;
        }
        // 删除组织关联
        orgSiteMapper.deleteBySiteId(siteId);
        // 删除用户关联
        userSiteMapper.deleteBySiteId(siteId);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSiteEf(Long orgId, Long userId) {
        if (orgId != null && userId != null) {
            // 删除组织关联关系
            orgSiteMapper.deleteByOrgId(orgId);
            // 删除用户关联关系
            userSiteMapper.deleteByUserId(userId);
        }
        return 1;
    }

    private void insertSiteEf(List<SysOrgSite> orgSites, List<SysUserSite> userSites) {
        if (!CollectionUtils.isEmpty(orgSites)) {
            orgSiteMapper.insertBatch(orgSites);
        }
        if (!CollectionUtils.isEmpty(userSites)) {
            userSiteMapper.insertBatch(userSites);
        }
    }
}
