package com.things.cgomp.device.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.pojo.device.DeviceCount;
import com.things.cgomp.device.api.RemoteDeviceService;
import com.things.cgomp.device.api.dto.UpdateChargeGridRulesDTO;
import com.things.cgomp.device.api.model.vo.ChargingAppPortDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RemoteDeviceFallbackFactory implements FallbackFactory<RemoteDeviceService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteDeviceFallbackFactory.class);
    @Override
    public RemoteDeviceService create(Throwable throwable) {
        log.error("设备桩服务调用失败:{}", throwable.getMessage());
        return new RemoteDeviceService() {

            @Override
            public R<ChargingAppPortDetailVO> getAppChargePort(Long portId, String source) {
                return R.fail("远程服务调用失败: " + throwable.getMessage());
            }

            @Override
            public R<ChargingAppPortDetailVO> getAppChargePortBySn(String portSn, String source) {
                return R.fail(
                        throwable.getMessage()
                );
            }

            @Override
            public R<Map<Long,DeviceInfo>> selectDeviceMap(List<Long> deviceIds) {
                return R.fail(
                        new HashMap<>(),
                        throwable.getMessage()
                );
            }

            @Override
            public R<DeviceInfo> selectDevice(Long deviceId) {
                return R.fail(
                        throwable.getMessage()
                );
            }

            @Override
            public R<String> insertGun(Long portId, Long insertTime, String vin) {
                return R.fail(
                        throwable.getMessage()
                );
            }

            @Override
            public R<?> updateRules(UpdateChargeGridRulesDTO rule) {
                return R.fail(
                        throwable.getMessage()
                );
            }

            @Override
            public R<DeviceCount> getPileDeviceCount(Long operatorId, Long siteId, String sn, String source) {
                return R.fail(
                        throwable.getMessage()
                );
            }
        };
    }
}
