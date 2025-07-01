package com.things.cgomp.system.domain.vo;

import com.things.cgomp.order.api.domain.OrderShareholders;
import com.things.cgomp.system.api.domain.SysOperator;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author things
 * @date 2025/2/28
 */
@Data
@Accessors(chain = true)
public class SysOperatorChildVO extends SysOperator {

    /**
     * 分成者列表
     */
    private List<OrderShareholders> shareholders = new ArrayList<>();
}
