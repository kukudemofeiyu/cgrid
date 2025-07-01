package com.things.cgomp.common.gw.device.context.api;

import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;

import java.util.List;

public interface IDeviceServiceApi extends DeviceMessageService {

    SyncMsgTemplate getSyncMsgTemple();

    DeviceInfo getDeviceInfo(DeviceInfo deviceInfo);

    DeviceInfo getDeviceInfoById(Long deviceId);

    List<DeviceInfo> getGuns(Long deviceId);


}
