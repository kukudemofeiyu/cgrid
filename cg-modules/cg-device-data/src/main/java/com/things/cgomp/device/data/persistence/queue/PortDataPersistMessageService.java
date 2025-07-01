package com.things.cgomp.device.data.persistence.queue;

import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.device.data.constants.RealDataType;
import com.things.cgomp.device.data.persistence.DeviceHistoryDataService;
import com.things.cgomp.device.data.persistence.DeviceRealDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author things
 */
@Slf4j
@Service("portDataPersist")
public class PortDataPersistMessageService extends AbrPersistMessageService<DevicePortData> {

    @Resource
    private DeviceRealDataService realDataService;
    @Resource
    private DeviceHistoryDataService historyDataService;

    @Override
    public void persistMessage(String name, List<DevicePortData> dataList) {
        try {
            // 存储历史数据
            historyDataService.writeDevicePortData(dataList);
            // 存储实时数据
            realDataService.saveBatch(dataList, RealDataType.MONITOR);
        }catch (Exception e){
            log.error("{},插入数据={},持久化设备数据失败", name, dataList.size(), e);
        }
    }
}
