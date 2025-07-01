package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class YkcChargeStandardSetOut extends YkcMessageOut  {
    /**
     *  设备编号
     */
    private String deviceNo;
    /**
     * 计费模型编号
     * 固定值：0100
     */
    private Integer chargeStandardModelNo = 100;

    /**
     * 费率数量
     */
    private byte feeSize;

    private List<YkcChargeStandardFeeOut> feeOutList;

    /**
     * 计损比例
     */
    private Byte lossCalculationRatio;
    /**
     * 半小时设置一个费率
     * 24个小时需要设置48个费率
     */
    private List<Integer> YkcTimeRangeFeeRate;

    public static YkcChargeStandardSetOut of(Integer frameSerialNo, String deviceNo,
                                             List<Integer> YkcTimeRangeFeeRate, List<YkcChargeStandardFeeOut> feeOutList) {
        return new YkcChargeStandardSetOut(frameSerialNo, deviceNo, YkcTimeRangeFeeRate, feeOutList);
    }
    private final static Integer TIME_RANGE_FEE_RATE_LENGTH = 48;
    private YkcChargeStandardSetOut(Integer frameSerialNo, String deviceNo,
                                    List<Integer> YkcTimeRangeFeeRate,
                                    List<YkcChargeStandardFeeOut> feeOutList) {
        super(frameSerialNo, DeviceOpConstantEnum.SET_CHARGE_STANDARD_CHECK.getOpCode(), true);
        if (Objects.isNull(YkcTimeRangeFeeRate) || YkcTimeRangeFeeRate.size() != TIME_RANGE_FEE_RATE_LENGTH) {
            throw new IllegalArgumentException("timeRangeFeeRate length must equals 48");
        }
        this.deviceNo = deviceNo;
        this.lossCalculationRatio = 0x00;
        this.YkcTimeRangeFeeRate = YkcTimeRangeFeeRate;
        this.feeOutList = feeOutList;
        this.feeSize = (byte) feeOutList.size();
    }

}
