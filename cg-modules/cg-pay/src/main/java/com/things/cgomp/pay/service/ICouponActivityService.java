package com.things.cgomp.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.dto.coupon.CouponActivityPageDTO;
import com.things.cgomp.pay.vo.coupon.CouponActivityVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券活动表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
public interface ICouponActivityService extends IService<CouponActivity> {

    Long saveActivity(
            CouponActivity activity
    );

    void grantCoupons(
            OrderInfo orderInfo
    );

    Map<Long, String> getNameMap(
            List<Long> ids
    );

    CouponActivity selectActivity(
            Long id
    );

    void editActivity(
            CouponActivity activity
    );

    void switchActivity(
            CouponActivity activity
    );

    PageInfo<CouponActivityVo> selectPage(
            CouponActivityPageDTO pageDTO
    );

}
