package com.things.cgomp.common.device.pojo.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Data
@Accessors(chain = true)
public class DevicePortVo {

    private Long deviceId;

    /**
     * 别名sn,用于设备通信
     */
    private String aliasSn;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 充电类型，0-快充，1-慢充
     */
    private Integer chargeType;

    /**
     * 当component=1,枪口状态，0-离线 1-故障 2-空闲 3-充电 4-已插枪 5-占用
     */
    private Integer portStatus;
    /**
     * 当component=1,枪口归位状态，0-否 1-是 2-未知
     */
    private Integer homeStatus;
    /**
     * 枪口状态持续时间，小时
     */
    private BigDecimal portStatusDuration;
    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 运营商名称
     */
    private String operatorName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 充电桩id
     */
    private Long pileId;

    /**
     * 充电桩编号
     */
    private String pileSn;

    /**
     * 充电桩名称
     */
    private String pileName;

    private Integer status;
    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 当前订单流水号
     */
    private String orderSn;

    public void setPortStatusDuration(BigDecimal portStatusDuration) {
        this.portStatusDuration = portStatusDuration.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
    }
}
