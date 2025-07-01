package com.things.cgomp.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.api.domain.SiteDiscountActivity;
import com.things.cgomp.pay.api.vo.DiscountCouponVo;
import com.things.cgomp.pay.dto.sitediscount.SiteDiscountActivityPageDTO;
import com.things.cgomp.pay.vo.sitediscount.SiteDiscountActivityVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 站点折扣活动表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
public interface ISiteDiscountActivityService extends IService<SiteDiscountActivity> {

    Map<Long, String> getActivityNameMap(
            List<Long> ids
    );

    List<DiscountCouponVo> getDiscountCoupons(
            Long operatorId,
            OrderInfo orderInfo
    );

    Long saveActivity(
            SiteDiscountActivity activity
    );

    SiteDiscountActivity getActivity(
            Long id
    );

    void editActivity(
            SiteDiscountActivity activity
    );

    List<SiteDiscountActivity> selectActivities(
         Long templateId
    );

    void deleteActivity(
            Long id
    );

    void switchActivity(
            Long id,
            Integer status
    );

    PageInfo<SiteDiscountActivityVo> selectPage(
            SiteDiscountActivityPageDTO pageDTO
    );
}
