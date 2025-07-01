package com.things.cgomp.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.pay.api.domain.Coupon;
import com.things.cgomp.pay.api.dto.CouponAppPageDTO;
import com.things.cgomp.pay.api.vo.DiscountCouponVo;
import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.dto.coupon.CouponPageDTO;
import com.things.cgomp.pay.vo.coupon.CouponVo;

import java.util.List;

/**
 * <p>
 * 优惠券表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-26
 */
public interface ICouponService extends IService<Coupon> {

    void grantCoupons(
            CouponActivity activity
    );

    PageInfo<CouponVo> selectPage(
            CouponPageDTO pageDTO
    );

    Coupon selectCoupon(
            Long id
    );

    List<DiscountCouponVo> selectSiteActivityAndCoupons(
            Long orderId
    );

    List<CouponVo> selectAppCoupon(CouponAppPageDTO pageDTO);
}
