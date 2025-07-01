package com.things.cgomp.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.device.api.model.vo.SiteAppInfoVO;
import com.things.cgomp.device.api.model.vo.SiteAppVO;
import com.things.cgomp.device.dto.site.SiteListDTO;
import com.things.cgomp.device.dto.site.SitePageDTO;
import com.things.cgomp.device.vo.SiteVo;
import com.things.cgomp.device.vo.site.SimpleSiteVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 站点表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
public interface SiteMapper extends BaseMapper<Site> {

    Long getOperatorId(
            @Param("id") Long id
    );

    List<SiteVo> selectSites(SitePageDTO pageDTO);

    List<SimpleSiteVo> selectSimpleSites(SiteListDTO siteDTO);

    List<SiteAppVO> selectSiteAppList(SiteListDTO siteListDTO);

    SiteAppInfoVO selectSiteAppInfo(Long siteId);
}
