package com.things.cgomp.device.service;

import com.things.cgomp.common.device.pojo.device.CommandBaseConfig;
import com.things.cgomp.common.device.pojo.device.DeviceCommandEnum;
import com.things.cgomp.common.device.pojo.device.push.PushInfo;
import com.things.cgomp.common.device.pojo.device.push.PushResult;

public interface IDevicePushService {

    /**
     * @param gridId                 桩设备id
     * @param portId                 枪设备id
     * @param pushMessageType        指令类型
     * @param context                配置
     * @return
     */
    PushResult push(Long gridId, Long portId,  DeviceCommandEnum pushMessageType, Object context) throws Exception;

}
