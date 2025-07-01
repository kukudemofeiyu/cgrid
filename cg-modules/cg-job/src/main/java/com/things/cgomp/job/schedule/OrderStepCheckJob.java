package com.things.cgomp.job.schedule;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.job.service.LockService;
import com.things.cgomp.order.api.RemoteOrderStepService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 订单执行步骤检查任务
 *
 * @author janson
 */
@Slf4j
@Component
public class OrderStepCheckJob {

    @Resource
    private LockService lockService;
    @Resource
    private RemoteOrderStepService remoteOrderStepService;

    private static final String orderStepLockKey = "order_step_check_lock";

    @XxlJob("checkOrderStep")
    public void checkOrderStep() {
        log.debug("@@@@@@@@@@@@@@@@@@@@@开始检查订单执行步骤 checkOrderStep");
        RLock lock = lockService.getLock(orderStepLockKey);
        if (lock == null) {
            return;
        }
        try {
            R<Boolean> r = remoteOrderStepService.checkAndProcessOrderStep(SecurityConstants.INNER);
            if (r == null || !r.success() || !r.getData()) {
                log.error("checkOrderStep 远程服务checkAndProcessOrderStep 调用出现异常, msg={}", r != null ? r.getMsg() : null);
                return;
            }
        } catch (Exception e) {
            log.error("checkOrderStep error, ", e);
        } finally {
            lock.unlock();
        }
        log.debug("@@@@@@@@@@@@@@@@@@@@@完成检查订单执行步骤 checkOrderStep");
    }
}
