package com.things.cgomp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.order.api.domain.OrderDiscount;
import com.things.cgomp.order.api.domain.OrderInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单折扣明细表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-04-01
 */
public interface IOrderDiscountService extends IService<OrderDiscount> {

    void saveDiscounts(
            List<Long> siteActivityIds,
            List<Long> couponIds,
            OrderInfo orderInfo
    );

    void saveDiscounts(
           Object config
    );

    List<OrderDiscount> getDiscounts(
            List<Long> siteActivityIds,
            List<Long> couponIds,
            OrderInfo orderInfo
    );

    List<OrderDiscount> selectDiscounts(
            Long orderId
    );

    Map<Long, List<OrderDiscount>> selectDiscountsMap(
            List<Long> orderIds
    );

}
