package com.things.cgomp.device.dto.monitor;

import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 站点监控设备数据
 *
 * @author things
 */
@Data
@Accessors(chain = true)
public class SiteMonitorDeviceResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充电枪ID
     */
    private Long deviceId;
    /**
     * 充电枪名称
     */
    private String name;
    /**
     * 充电枪编号
     */
    private String aliasSn;
    /**
     * 充电桩名称
     */
    private String pileName;
    /**
     * 充电桩编码
     */
    private String pileSn;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 充电枪状态
     * 0-离线 1-故障 2-空闲 3-充电 4-已插枪 5-占用
     */
    private Integer portStatus;
    /**
     * 功率
     */
    private BigDecimal power;
    /**
     * 电压
     */
    private BigDecimal voltage;
    /**
     * 电流
     */
    private BigDecimal current;
    /**
     * SOC
     */
    private Integer soc;

    public void setRealData(DevicePortData realData) {
        if (realData == null) {
            this.power = zero();
            this.current = zero();
            this.voltage = zero();
            this.soc = 0;
        } else {
            Double p = realData.getPower();
            if (p == null) {
                this.power = zero();
            } else {
                this.power = new BigDecimal(p).setScale(2, RoundingMode.HALF_UP);
            }
            Double c = realData.getCurrent();
            if (c == null) {
                this.current = zero();
            } else {
                this.current = new BigDecimal(c).setScale(2, RoundingMode.HALF_UP);
            }
            Double v = realData.getVoltage();
            if (v == null) {
                this.voltage = zero();
            } else {
                this.voltage = new BigDecimal(v).setScale(2, RoundingMode.HALF_UP);
            }
            Integer soc = realData.getSoc();
            if (soc == null) {
                this.soc = 0;
            } else {
                this.soc = soc;
            }
        }
    }

    private BigDecimal zero() {
        return BigDecimal.ZERO.setScale(2, RoundingMode.DOWN);
    }
}
