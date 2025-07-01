package com.things.cgomp.common.device.pojo.device;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class StartChargingConfigDTO  extends CommandBaseConfig {

    /**
     * 充电枪设备编号，app入参
     */
    private Long portId;

    /**
     * 用户账户余额, 保留两位小数点
     */
    private Double accountBalance;
    /**
     * 对应于app_user主键
     */
    private Long userId;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 车牌号码
     */
    private String licensePlateNumber;

    /**
     * 交易流水号32位
     */
    private String orderNo;

    /**
     *  卡号
     */
    private String cardNo;

    /**
     * 物理卡号
     */
    private String iccid;


    /**
     * 充电时长， 0是自动充满 ,app入参
     */
    private Long chargePeriod;
    /**
     * 支付方式
     */
    private Integer paymentType;










}
