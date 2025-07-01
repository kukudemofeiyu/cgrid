package com.things.cgomp.order.service.impl;

import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.service.IOrderInfoService;
import com.things.cgomp.order.service.IOrderStepService;
import com.things.cgomp.order.step.OrderPaidAfterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author things
 */
@Slf4j
@Service
public class OrderStepServiceImpl implements IOrderStepService {

    @Resource
    private IOrderInfoService orderInfoService;
    @Resource
    private OrderPaidAfterHandler orderPaidAfterHandler;

    @Override
    public boolean checkAndProcessOrderStep() {
        try {
            // 查询流程缺失的充电订单
            List<OrderInfo> orders = orderInfoService.selectProcessLossOrders();
            if (CollectionUtils.isEmpty(orders)) {
                return true;
            }
            orders.forEach(order -> {
                // 执行缺失的流程
                orderPaidAfterHandler.process(order);
                log.info("checkAndProcessOrderStep 订单ID：{}， 订单号【{}】已检查流程完成", order.getId(), order.getSn());
            });
        } catch (Exception e) {
            log.error("OrderStepServiceImpl checkAndProcessOrderStep fail", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean checkAndProcessOrderStep(Long orderId) {
        try {
            OrderInfo order = orderInfoService.getById(orderId);
            if (order == null) {
                return false;
            }
            // 执行缺失的流程
            orderPaidAfterHandler.process(order);
        } catch (Exception e) {
            log.error("OrderStepServiceImpl checkAndProcessOrderStep fail, orderId={}", orderId, e);
            return false;
        }
        return true;
    }
}
