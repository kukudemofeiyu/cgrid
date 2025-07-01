package com.things.cgomp.order.mapper;

import com.things.cgomp.order.api.domain.OrderDiscount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单折扣明细表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-04-01
 */
public interface OrderDiscountMapper extends BaseMapper<OrderDiscount> {

    List<OrderDiscount> selectDiscounts(
            @Param("orderId") Long orderId
    );

    List<OrderDiscount> selectDiscountsByOrderIds(
            @Param("orderIds") List<Long> orderIds
    );
}
