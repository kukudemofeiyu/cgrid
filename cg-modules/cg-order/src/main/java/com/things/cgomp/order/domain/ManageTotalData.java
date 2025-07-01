package com.things.cgomp.order.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ManageTotalData extends ManageBaseData {

    /**
     * 总收益
     */
    private BigDecimal totalIncome;
    /**
     * 实际收益
     */
    private BigDecimal realIncome;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 设备总数
     */
    private Long deviceCount;
    /**
     * 在线设备数量
     */
    private Long onlineCount;
    /**
     * 离线设备数量
     */
    private Long offlineCount;

    public void setDeviceEmpty(){
        this.deviceCount = 0L;
        this.onlineCount = 0L;
        this.offlineCount = 0L;
    }
}
