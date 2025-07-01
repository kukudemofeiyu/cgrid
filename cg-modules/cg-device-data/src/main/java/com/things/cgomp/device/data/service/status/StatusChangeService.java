package com.things.cgomp.device.data.service.status;

import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.mq.common.Metadata;

/**
 * @author things
 */
public interface StatusChangeService {

    void process(DevicePortStatus portStatus, Metadata metadata);
}
