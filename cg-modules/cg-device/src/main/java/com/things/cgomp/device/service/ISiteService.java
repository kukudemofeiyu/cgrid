package com.things.cgomp.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.device.api.model.vo.SiteAppInfoVO;
import com.things.cgomp.device.api.model.vo.SiteAppVO;
import com.things.cgomp.device.dto.site.SiteListDTO;
import com.things.cgomp.device.dto.site.SitePageDTO;
import com.things.cgomp.device.vo.SiteVo;
import com.things.cgomp.device.vo.site.SimpleSiteVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 站点表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
public interface ISiteService extends IService<Site> {

    Long addSite(
            Site site
    );

    void editSite(
            Site site
    );

    void deleteSite(
          Long id
    );

    Site selectSite(
            Long id
    );

    PageInfo<SiteVo> selectPage(
            SitePageDTO sitePageDTO
    );

    List<SimpleSiteVo> selectSimpleSites(
            SiteListDTO siteDTO
    );

    Map<Long,Site> selectSiteMap(
           List<Long> ids
    );

    Long getOperatorId(
            Long id
    );

    List<SiteAppVO> selectSiteAppList(SiteListDTO siteListDTO);

    SiteAppInfoVO selectSiteAppInfo(Long siteId, Float lat, Float lng);

    Long getSiteOperatorId(Long siteId);
}
