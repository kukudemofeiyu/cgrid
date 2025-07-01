package com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit;

import com.things.cgomp.pay.enums.ReceiveLimitTypeEnum;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.impl.DayReceiveLimit;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.impl.NoLimitReceiveLimit;
import com.things.cgomp.pay.service.impl.grantcoupon.impl.receivelimit.impl.TotalReceiveLimit;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ReceiveLimitFactory {

    @Resource
    private ApplicationContext applicationContext;

    public ReceiveLimit createReceiveLimit(Integer type) {
        ReceiveLimitTypeEnum typeEnum = ReceiveLimitTypeEnum.getEnum(type);
        if (typeEnum == null) {
            return null;
        }

        ReceiveLimit receiveLimit = null;
        switch (typeEnum) {
            case NO_LIMIT:
                receiveLimit = applicationContext.getBean(NoLimitReceiveLimit.class);
                break;
            case DAY_LIMIT:
                receiveLimit = applicationContext.getBean(DayReceiveLimit.class);
                break;
            case TOTAL_LIMIT:
                receiveLimit = applicationContext.getBean(TotalReceiveLimit.class);
                break;
        }

        return receiveLimit;

    }
}
