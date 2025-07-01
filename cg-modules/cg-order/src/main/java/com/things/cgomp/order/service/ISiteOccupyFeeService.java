package com.things.cgomp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.order.domain.SiteOccupyFee;
import com.things.cgomp.order.dto.SiteOccupyFeeDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 站点占位费表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-02
 */
public interface ISiteOccupyFeeService extends IService<SiteOccupyFee> {

    SiteOccupyFeeDTO selectOccupyFee(Long siteId);

    Long saveOccupyFee(
            SiteOccupyFeeDTO siteOccupyFee
    );

    BigDecimal calculateFee(
            Long siteId,
            Integer type,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
