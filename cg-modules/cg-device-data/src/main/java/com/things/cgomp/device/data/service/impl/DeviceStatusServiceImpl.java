package com.things.cgomp.device.data.service.impl;

import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.dao.td.domain.PortStatusData;
import com.things.cgomp.common.device.enums.PortStatusOperate;
import com.things.cgomp.common.device.pojo.device.DevicePortStatusDTO;
import com.things.cgomp.common.device.service.DevicePortStatusService;
import com.things.cgomp.device.data.convert.PortStatusConvert;
import com.things.cgomp.device.data.persistence.queue.PortStatusPersistMessageService;
import com.things.cgomp.device.data.service.IDeviceStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author things
 */
@Service
public class DeviceStatusServiceImpl implements IDeviceStatusService {

    @Resource
    private DevicePortStatusService portStatusService;
    @Resource
    private PortStatusPersistMessageService portStatusPersistMessageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkAndModifyPortStatus(DevicePortStatusDTO portStatusDTO, PortStatusOperate operate) {
        DevicePortStatus portStatus = portStatusService.checkAndModifyPortStatus(portStatusDTO, operate);
        if (portStatus != null) {
            PortStatusData portStatusData = PortStatusConvert.INSTANCE.convertToPersist(portStatus, portStatusDTO.getEventTime());
            // 保存充电枪状态
            portStatusPersistMessageService.persist(portStatusData);
        }
    }

    @Override
    public DevicePortStatus getPortRealStatus(Long portId) {
        return portStatusService.getPortStatus(portId);
    }

    @Override
    public void modifyPortVin(DevicePortStatusDTO portStatusDTO) {
        DevicePortStatus portStatus = portStatusService.modifyVin(portStatusDTO);
        if (portStatus != null) {
            PortStatusData portStatusData = PortStatusConvert.INSTANCE.convertToPersist(portStatus, portStatusDTO.getEventTime());
            // 保存充电枪状态
            portStatusPersistMessageService.persist(portStatusData);
        }
    }
}
