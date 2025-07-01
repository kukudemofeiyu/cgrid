package com.things.cgomp.gateway.device.broker.ykc.utils;

import com.things.cgomp.common.core.utils.SpringUtils;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcMessageIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcMessageOut;
import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceMessageHandlerEnum;
import com.things.cgomp.gateway.device.broker.ykc.message.YkcAbstractMessageHandler;

public class DeviceOptHandlerHelper {

    /**
     * 获取handler
     */
    public static YkcAbstractMessageHandler<YkcMessageIn, YkcMessageOut> getHandler(Integer platformOpCode) {

        DeviceMessageHandlerEnum[] values = DeviceMessageHandlerEnum.values();
        for (DeviceMessageHandlerEnum handlerEnum:
             values) {
            if(handlerEnum.getOpCode().equals(platformOpCode)){
                return (YkcAbstractMessageHandler<YkcMessageIn, YkcMessageOut>)SpringUtils.getBean(handlerEnum.getHandlerName());
            }
        }
        return null;
    }

}
