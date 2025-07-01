package com.things.cgomp.order.step.impl;

import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.enums.ProcessStepEnum;
import com.things.cgomp.order.step.OrderPaidAfterProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 订单返积分处理器
 * @author things
 */
@Component
@Order(OrderPaidAfterProcessor.ORDER_INTEGRAL)
public class OrderIntegralProcessor implements OrderPaidAfterProcessor {

    @Override
    public void process(OrderInfo order) {
        // 返积分逻辑
    }

    @Override
    public ProcessStepEnum getStep() {
        return ProcessStepEnum.UNRETURNED_INTEGRAL;
    }
}
