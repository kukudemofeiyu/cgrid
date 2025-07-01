package com.things.cgomp.common.device.dao.device.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单消息请求对象
 * @author things
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("device_charge_record_exception")
public class DeviceChargeRecordException {

    @TableId
    private Long id;

    /**
     * VIN码
     */
    private String vin;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 充电桩ID
     */
    private Long pileId;
    /**
     * 充电口ID
     */
    private Long portId;

    /**
     * 卡号
     * 物理卡号
     */
    private String cardNo;

    /**
     * 充电开始时间
     */
    private Date startTime;
    /**
     * 充电结束时间
     */
    private Date endTime;
    /**
     * 充电量
     */
    private BigDecimal electricity;
    /**
     * 消费金额
     */
    private BigDecimal amount;
    /**
     * 交易标识
     * 1app充电  2卡启动
     */
    private Integer flag;
    /**
     * 交易时间
     */
    private Date orderTime;

    /**
     * 结束原因编码
     */
    private Integer endReasonCode;
    /**
     * 结束原因描述
     */
    private String endReasonDesc;
    /**
     * 费率信息
     */
    private String feeRateJson;

    private Date commitTime;


    private Date eventTs;
}
