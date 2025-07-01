package com.things.cgomp.device.data.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.device.data.api.RemoteDeviceDataService;
import com.things.cgomp.device.data.api.domain.DevicePortAllHistoryData;
import com.things.cgomp.device.data.api.domain.DevicePortAllRealData;
import com.things.cgomp.device.data.api.domain.RealDataQueryReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteDeviceDataFallbackFactory implements FallbackFactory<RemoteDeviceDataService> {

    @Override
    public RemoteDeviceDataService create(Throwable throwable) {
        log.error("设备数据服务调用失败:{}", throwable.getMessage());
        return new RemoteDeviceDataService() {

            @Override
            public R<DevicePortData> selectRealDataByOrderNo(RealDataQueryReq req, String source) {
                return R.fail("查询设备实时数据失败: " + throwable.getMessage());
            }

            @Override
            public R<List<DevicePortData>> selectRealDataByOrderNo(String reqListStr, String source) {
                return R.fail("批量查询设备实时数据失败: " + throwable.getMessage());
            }

            @Override
            public R<DevicePortAllRealData> selectAllRealDataByOrderNo(RealDataQueryReq req, String source) {
                return R.fail("查询所有设备实时数据失败: " + throwable.getMessage());
            }

            @Override
            public R<List<DevicePortData>> selectHistoryByOrderNo(Long deviceId, String orderNo, String source) {
                return R.fail("根据订单号查询设备历史数据失败: " + throwable.getMessage());
            }

            @Override
            public R<List<DevicePortData>> selectHistoryByTradeSn(Long deviceId, String tradeSn, String source) {
                return R.fail("根据订单号查询设备历史数据失败: " + throwable.getMessage());
            }

            @Override
            public R<DevicePortAllHistoryData> selectAllHistoryByTradeSn(Long deviceId, String tradeSn, String source) {
                return R.fail("根据订单号查询设备全部历史数据失败: " + throwable.getMessage());
            }
        };
    }
}
