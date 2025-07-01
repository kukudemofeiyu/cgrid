package com.things.cgomp.device.data.persistence.queue;

import com.things.cgomp.common.device.dao.td.domain.DeviceChargeData;
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
@Service("deviceChargeDataMessageService")
public class DeviceChargeDataMessageService extends AbrPersistMessageService<DeviceChargeData> {

    @Resource
    private DeviceHistoryDataService historyDataService;
    @Resource
    private DeviceRealDataService realDataService;

    @Override
    public void persistMessage(String name, List<DeviceChargeData> dataList) {
        try {
            // 存储历史数据
            historyDataService.writeDeviceChargeDataLog(dataList);
            // 存储实时数据
            realDataService.saveBatch(dataList, RealDataType.BMS_DEMAND);
        }catch (Exception e){
            log.error("{},插入数据={},持久化充电数据失败", name, dataList.size(), e);
        }
    }
}
