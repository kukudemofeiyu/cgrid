package com.things.cgomp.device.data.persistence.queue;

import com.things.cgomp.common.device.dao.td.domain.DeviceCmdLogData;
import com.things.cgomp.device.data.persistence.DeviceHistoryDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author things
 */
@Slf4j
@Service("deviceCmdLogPersistMessageService")
public class DeviceCmdLogPersistMessageService extends AbrPersistMessageService<DeviceCmdLogData> {

    @Resource
    private DeviceHistoryDataService historyDataService;

    @Override
    public void persistMessage(String name, List<DeviceCmdLogData> dataList) {
        try {
            // 存储历史数据
            historyDataService.writeDeviceCmdLog(dataList);
        }catch (Exception e){
            log.error("{},插入数据={},持久化设备交互日志失败", name, dataList.size(), e);
        }
    }
}
