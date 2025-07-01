package com.things.cgomp.order.dto;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OrderFinanceQueryDTO extends BaseEntity {

    /**
     * 类型
     * 1-按运营商统计 2-按站点统计 3-按充电桩统计
     */
    private Integer type;
}
