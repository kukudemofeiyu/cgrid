package com.things.cgomp.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.order.domain.SiteOccupyFee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 站点占位费表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-02
 */
public interface SiteOccupyFeeMapper extends BaseMapper<SiteOccupyFee> {

    SiteOccupyFee selectFee(
            @Param("siteId") Long siteId,
            @Param("type") Integer type
    );

    List<SiteOccupyFee> selectFees(
            @Param("siteId") Long siteId
    );

    SiteOccupyFee selectEnableFee(
            @Param("siteId") Long siteId,
            @Param("type") Integer type
    );
}
