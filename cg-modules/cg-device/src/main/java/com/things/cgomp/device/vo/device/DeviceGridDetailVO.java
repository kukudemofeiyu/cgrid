package com.things.cgomp.device.vo.device;

import com.things.cgomp.common.device.pojo.device.DeviceDTO;
import com.things.cgomp.common.device.pojo.device.DevicePortVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 充电桩详情返回对象
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class DeviceGridDetailVO extends DeviceDTO {

    /**
     * 充电枪信息
     */
    private List<DevicePortVo> ports;
}
