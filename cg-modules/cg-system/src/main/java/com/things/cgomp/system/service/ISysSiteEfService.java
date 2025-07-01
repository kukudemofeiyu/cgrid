package com.things.cgomp.system.service;

import com.things.cgomp.system.api.domain.SysSiteEf;

import java.util.List;

/**
 * @author things
 */
public interface ISysSiteEfService {

    /**
     * 根据用户查询关联站点ID
     * @param userId 用户ID
     * @return 站点ID集合
     */
    List<Long> selectSiteIdsByUserId(Long userId);

    /**
     * 新增站点关联，包含组织站点、用户站点
     * @param siteEf 站点关联对象
     * @return int
     */
    int addSiteEf(SysSiteEf siteEf);

    /**
     * 修改站点关联，包含组织站点、用户站点
     * @param siteEf 站点关联对象
     * @return int
     */
    int updateSiteEf(SysSiteEf siteEf);

    /**
     * 根据站点ID删除站点关联
     * @param siteId 站点ID
     * @return int
     */
    int deleteSiteEfBySite(Long siteId);

    /**
     * 根据组织ID和用户ID删除站点关联
     * @param orgId 组织ID
     * @param userId 用户ID
     * @return int
     */
    int deleteSiteEf(Long orgId, Long userId);
}
