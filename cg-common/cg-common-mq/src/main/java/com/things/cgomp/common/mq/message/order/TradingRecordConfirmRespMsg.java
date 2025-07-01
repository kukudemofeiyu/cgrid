package com.things.cgomp.common.mq.message.order;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 订单消息请求对象
 *
 * @author things
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TradingRecordConfirmRespMsg extends AbstractBody {

    /**
     * 事务
     */
    private String transactionId;

    /**
     * 成功：200
     */
    private Integer success;

    /**
     * 订单号
     */
    private String orderNo;
}
