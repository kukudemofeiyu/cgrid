package com.things.cgomp.common.device.pojo.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 历史数据查询请求对象
 * @author things
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDataQueryReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单流水号
     */
    private String tradeSn;
    /**
     * 属性key
     */
    private String key;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 表名
     */
    private String table;
}
