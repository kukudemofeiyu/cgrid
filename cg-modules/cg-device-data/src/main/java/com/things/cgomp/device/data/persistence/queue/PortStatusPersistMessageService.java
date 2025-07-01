package com.things.cgomp.device.data.persistence.queue;

import com.things.cgomp.common.device.dao.td.domain.PortStatusData;
import com.things.cgomp.device.data.persistence.DeviceHistoryDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author things
 */
@Slf4j
@Service("portStatusPersist")
public class PortStatusPersistMessageService extends AbrPersistMessageService<PortStatusData> {

    @Resource
    private DeviceHistoryDataService historyDataService;

    @Override
    public void persistMessage(String name, List<PortStatusData> dataList) {
        try {
            // 存储历史数据
            historyDataService.writeDevicePortStatus(dataList);
        }catch (Exception e){
            log.error("{},插入数据={},持久化充电枪状态失败", name, dataList.size(), e);
        }
    }
}
