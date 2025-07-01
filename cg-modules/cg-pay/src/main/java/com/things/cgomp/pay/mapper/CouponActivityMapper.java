package com.things.cgomp.pay.mapper;

import com.things.cgomp.pay.domain.CouponActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.pay.dto.coupon.CouponActivityPageDTO;
import com.things.cgomp.pay.vo.coupon.CouponActivityVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 优惠券活动表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
public interface CouponActivityMapper extends BaseMapper<CouponActivity> {

    List<CouponActivityVo> selectActivities(
            CouponActivityPageDTO pageDTO
    );

    List<CouponActivity> selectEnableActivities(
            @Param("operatorId") Long operatorId,
            @Param("siteId") Long siteId,
            @Param("time") LocalDateTime time
    );
}
