package com.things.cgomp.order.step.impl;

import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.enums.ProcessStepEnum;
import com.things.cgomp.order.step.OrderPaidAfterProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 订单数据统计处理器
 * @author things
 */
@Component
@Order(OrderPaidAfterProcessor.ORDER_STATISTICS)
public class OrderStatisticsProcessor implements OrderPaidAfterProcessor {

    @Override
    public void process(OrderInfo order) {
        // 数据统计业务逻辑
    }

    @Override
    public ProcessStepEnum getStep() {
        return ProcessStepEnum.NO_DATA_STATISTICS;
    }
}
