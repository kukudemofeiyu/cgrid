package com.things.cgomp.device.data.service.status.impl;

import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.dao.device.domain.PortChangeValue;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.device.data.service.status.AbrStatusChangeService;
import org.springframework.stereotype.Service;

/**
 * @author things
 */
@Service("orderSnChangeService")
public class OrderSnChangeService extends AbrStatusChangeService {

    @Override
    public void process(DevicePortStatus portStatus, Metadata metadata) {
        PortChangeValue changeValue = portStatus.getChangeValue();
        if (changeValue == null || changeValue.getOrderSn() == null) {
            return;
        }
        // 检查未结算订单
        checkUnSettledOrder(portStatus.getPortId(), changeValue.getOrderSn());
    }
}
