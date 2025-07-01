package com.things.cgomp.device.vo.device;

import com.things.cgomp.device.vo.device.trend.CommonTrendData;
import com.things.cgomp.device.vo.device.trend.ElectricityTrend;
import com.things.cgomp.device.vo.device.trend.TemperatureTrendData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 充电枪电能数据
 *
 * @author things
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DevicePortTrendDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 电压趋势数据
     */
    private CommonTrendData voltageTrend;
    /**
     * 电流趋势数据
     */
    private CommonTrendData currentTrend;
    /**
     * 温度趋势数据
     */
    private TemperatureTrendData temperatureTrend;
    /**
     * 电量趋势数据
     */
    private ElectricityTrend electricityTrend;

    public DevicePortTrendDataVO(Long deviceId){
        this.deviceId = deviceId;
        this.voltageTrend = new CommonTrendData();
        this.currentTrend = new CommonTrendData();
        this.temperatureTrend = new TemperatureTrendData();
        this.electricityTrend = new ElectricityTrend();
    }
}
