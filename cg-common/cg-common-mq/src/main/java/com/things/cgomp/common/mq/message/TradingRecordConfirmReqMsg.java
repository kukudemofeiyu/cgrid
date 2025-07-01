package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单消息请求对象
 * @author things
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TradingRecordConfirmReqMsg extends AbstractBody {

    /**
     * 事务
     */
    private String transactionId;

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
    private Long startTime;
    /**
     * 充电结束时间
     */
    private Long endTime;
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
     * TODO、待确认此时间
     */
    private Long orderTime;

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
    private List<ChargeFeeRate> feeRates;
}
