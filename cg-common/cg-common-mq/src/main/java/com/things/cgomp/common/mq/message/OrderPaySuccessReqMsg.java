package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderPaySuccessReqMsg extends AbstractBody {

    /**
     * 订单号列表
     */
    private List<Long> orderIds;

    private String payOrderId;

    /**
     * 支付时间
     */
    private Long payTime;

    private Object config;

}
