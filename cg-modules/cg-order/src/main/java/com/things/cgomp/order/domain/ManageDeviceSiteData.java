package com.things.cgomp.order.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ManageDeviceSiteData extends ManageBaseData {

    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备编号
     */
    private String deviceSn;
    /**
     * 累计费用
     */
    private BigDecimal totalFee;
    /**
     * 运行状态
     * 1在线 0离线
     */
    private Integer runStatus;

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee.setScale(2, RoundingMode.DOWN);
    }
}
