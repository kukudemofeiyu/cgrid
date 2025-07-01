package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.*;
import lombok.experimental.Accessors;

/**
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DrawGunReqMsg extends AbstractBody {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 拔枪时间
     */
    private Long drawGunTime;

    /**
     * 插枪时间
     */
    private Long insertTime;

    private String vin;

    /**
     * 充电桩ID
     */
    private Long pileId;
    /**
     * 充电口ID
     */
    private Long portId;
}
