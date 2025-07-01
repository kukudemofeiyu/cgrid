package com.things.cgomp.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.pay.api.domain.Coupon;
import com.things.cgomp.pay.dto.coupon.CouponPageDTO;
import com.things.cgomp.pay.vo.coupon.CouponVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 优惠券表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-26
 */
public interface CouponMapper extends BaseMapper<Coupon> {

    List<CouponVo> selectCoupons(
            CouponPageDTO pageDTO
    );

    List<Coupon> selectAvailableCoupons(
            @Param("operatorId") Long operatorId,
            @Param("userId") Long userId,
            @Param("siteId") Long siteId,
            @Param("time") LocalDateTime time
    );

}
