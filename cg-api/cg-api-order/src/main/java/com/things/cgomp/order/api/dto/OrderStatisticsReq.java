package com.things.cgomp.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 站点统计数据请求对象
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderStatisticsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 订单开始时间
     */
    private String beginTime;
    /**
     * 订单结束时间
     */
    private String endTime;
    /**
     * 充电桩ID
     */
    private Long pileId;
    /**
     * 充电枪ID
     */
    private Long portId;

    public void clearTime() {
        this.beginTime = null;
        this.endTime = null;
    }
}
