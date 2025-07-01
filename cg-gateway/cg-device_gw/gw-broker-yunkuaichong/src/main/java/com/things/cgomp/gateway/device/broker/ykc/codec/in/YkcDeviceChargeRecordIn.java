package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import com.things.cgomp.common.device.enums.YkcDeviceChargeStopReasonEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备充电记录
 */
@Data
@Builder
public class YkcDeviceChargeRecordIn extends YkcMessageIn {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 设备编号
     */
    private String gunNo;
    /**
     *  开始充电时间
     */
    private LocalDateTime startTime;
    /**
     *  结束充电时间
     */
    private LocalDateTime endTime;

    /**
     * 充电电量
     */
    private BigDecimal chargeElectricity;

    /**
     * 计损总电量
     */
    private BigDecimal lossElectricity;
    /**
     * 充电费用
     */
    private BigDecimal chargeFee;

    private String vin;

    private Integer orderChargeType;
    /**
     * 停止原因
     */
    private Integer stopReason;
    /**
     * 卡号
     */
    private String cardNo;

    private List<ChargeRecordFee> chargeRecordFees;

    private LocalDateTime orderTime;


    @Data
    public static class ChargeRecordFee{
        /**
         * 费率单价
         */
        private BigDecimal singleFee;

        /**
         *  费率电量
         */
        private BigDecimal electricity;

        /**
         * 计损电量
         */
        private BigDecimal lossElectricity;

        /**
         * 金额
         */
        private BigDecimal totalFee;

    }


}
