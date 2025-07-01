package com.things.cgomp.device.data.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 实时数据查询请求对象
 * @author things
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RealDataQueryReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充电枪ID
     */
    private Long deviceId;
    /**
     * 订单流水号
     */
    private String orderSn;
}
