package com.things.cgomp.pay.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.pay.api.domain.Coupon;
import com.things.cgomp.pay.api.dto.CouponAppPageDTO;
import com.things.cgomp.pay.api.vo.DiscountCouponVo;
import com.things.cgomp.pay.dto.coupon.CouponPageDTO;
import com.things.cgomp.pay.service.ICouponService;
import com.things.cgomp.pay.vo.coupon.CouponVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 优惠券表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-26
 */
@RestController
@RequestMapping(value = "/coupon",name = "优惠券")
public class CouponController extends BaseController {

    @Resource
    private ICouponService couponService;

    @GetMapping(value = "siteActivityAndCoupons", name = "查询站点活动和优惠券")
    public R<List<DiscountCouponVo>> selectSiteActivityAndCoupons(
            @RequestParam(value = "orderId") Long orderId
    ) {
        List<DiscountCouponVo> discountCoupons = couponService.selectSiteActivityAndCoupons(
                orderId
        );
        return R.ok(discountCoupons);
    }

    @GetMapping(value = "page", name = "优惠券分页列表")
    public R<PageInfo<CouponVo>> selectPage(
            CouponPageDTO pageDTO
    ) {
        PageInfo<CouponVo> page = couponService.selectPage(
                pageDTO
        );
        return R.ok(page);
    }

    @GetMapping(value = "detail", name = "查询优惠券")
    public R<Coupon> selectCoupon(
            @RequestParam Long id
    ) {
        Coupon coupon = couponService.selectCoupon(
                id
        );
        return R.ok(coupon);
    }
    /**
     * App优惠券分页
     */
    @GetMapping(value = "app/page", name = "优惠券分页列表")
    public R<PageInfo<CouponVo>> selectAppPage(
            CouponAppPageDTO pageDTO
    ) {
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {
            List<CouponVo> coupons = couponService.selectAppCoupon(pageDTO);
            return R.ok(new PageInfo<>(coupons));
        }
    }
}
