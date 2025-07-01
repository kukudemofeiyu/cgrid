package com.things.cgomp.order.step;

import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.enums.OrderTypeEnum;
import com.things.cgomp.order.enums.ProcessStepEnum;
import com.things.cgomp.order.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单支付完成后处理步骤统一处理器
 * 订单支付完成后调用此方法进行后续操作
 *
 * @author things
 */
@Slf4j
@Component
public class OrderPaidAfterHandler {

    @Resource
    private List<OrderPaidAfterProcessor> paidAfterProcessors;
    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private IOrderInfoService orderInfoService;

    public void process(OrderInfo order) {
        try {
            if (!OrderTypeEnum.REAL_TIME.getType().equals(order.getOrderType()) || CollectionUtils.isEmpty(paidAfterProcessors)) {
                return;
            }
            if (ProcessStepEnum.FIGURED_OUT.getType().equals(order.getProcessStep())) {
                // 流程已完成
                return;
            }
            boolean modifyFlag = false;
            for (int i = 1; i <= paidAfterProcessors.size(); i++) {
                OrderPaidAfterProcessor processor = paidAfterProcessors.get(i - 1);
                if (!order.getProcessStep().equals(processor.getStep().getType())) {
                    continue;
                }
                try {
                    processor.process(order);
                    // 标记为更新
                    modifyFlag = true;
                    // 修改订单step
                    if (i != paidAfterProcessors.size()) {
                        // 设置订单步骤为下一个流程
                        OrderPaidAfterProcessor nextProcessor = paidAfterProcessors.get(i);
                        order.setProcessStep(nextProcessor.getStep().getType());
                    } else {
                        // 已完成所有步骤
                        order.setProcessStep(ProcessStepEnum.FIGURED_OUT.getType());
                        order.setProcessLoss(0);
                        break;
                    }
                } catch (Exception e) {
                    log.error("order step process error, order={}, step={}", order, processor.getStep(), e);
                    // 跳出处理流程
                    break;
                }
            }
            if (modifyFlag) {
                // 修改订单,使用版本号更新，此处需先查询一次订单防止订单已被修改
                OrderInfo orderInfo = orderInfoService.getById(order);
                orderInfo.setProcessStep(order.getProcessStep());
                orderInfo.setProcessLoss(order.getProcessLoss());
                orderInfoService.updateById(orderInfo);
            }
        } catch (Exception e) {
            log.error("OrderPaidAfterHandler process error, order={}", order, e);
        }
    }
}
