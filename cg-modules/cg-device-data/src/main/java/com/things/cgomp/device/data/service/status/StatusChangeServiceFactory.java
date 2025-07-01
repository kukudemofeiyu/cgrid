package com.things.cgomp.device.data.service.status;

import com.things.cgomp.common.device.enums.PortStatusChangeFlag;
import com.things.cgomp.device.data.service.status.impl.OrderSnChangeService;
import com.things.cgomp.device.data.service.status.impl.PortDrawnService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author things
 */
@Component
public class StatusChangeServiceFactory {

    @Resource
    private ApplicationContext applicationContext;

    public StatusChangeService getChange(PortStatusChangeFlag changeFlag) {
        if (changeFlag == null) {
            return null;
        }
        StatusChangeService changeService = null;

        switch (changeFlag){
            case order_change:
                changeService = applicationContext.getBean(OrderSnChangeService.class);
                break;
            case drawn:
                changeService = applicationContext.getBean(PortDrawnService.class);

                break;
        }
        return changeService;
    }
}
