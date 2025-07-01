package com.things.cgomp.order.step;

import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.enums.ProcessStepEnum;

/**
 * 订单处理顺序：
 *     分佣 -》 积分 -》数据统计
 * @author things
 */
public interface OrderPaidAfterProcessor {

    /** 分佣顺序 **/
    int ORDER_COMMISSION = 10;
    /** 积分顺序 **/
    int ORDER_INTEGRAL = 20;
    /** 数据统计顺序 **/
    int ORDER_STATISTICS = 30;

    /**
     * 订单处理
     * @param order 订单对象
     */
    void process(OrderInfo order);
    /**
     * 获取步骤
     * @return {@link ProcessStepEnum}
     */
    ProcessStepEnum getStep();
}
