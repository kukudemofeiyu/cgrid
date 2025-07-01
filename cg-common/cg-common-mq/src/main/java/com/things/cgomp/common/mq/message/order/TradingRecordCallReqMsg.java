package com.things.cgomp.common.mq.message.order;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 交易记录召唤请求消息
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TradingRecordCallReqMsg extends AbstractBody {

   /**
    * 订单号
    */
   private String orderSn;
   /**
    * 充电桩ID
    */
   private Long pileId;
   /**
    * 充电枪ID
    */
   private Long portId;
}
