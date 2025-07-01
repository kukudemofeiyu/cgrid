package com.things.cgomp.common.device.pojo.device;

import com.things.cgomp.common.device.dao.td.domain.SingleTsValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 充电枪指定KEY数据
 *
 * @author things
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DevicePortKeyHistoryData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 查询的KEY
     */
    private String key;
    /**
     * 查询的KEY值列表
     */
    private List<SingleTsValue> values;

    public DevicePortKeyHistoryData(Long deviceId, String key){
        this.deviceId = deviceId;
        this.key = key;
    }
}
