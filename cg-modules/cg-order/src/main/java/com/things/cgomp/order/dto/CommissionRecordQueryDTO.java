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
public class CommissionRecordQueryDTO extends BaseEntity {

    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 充电桩ID
     */
    private Long pileId;
    /**
     * 充电枪ID
     */
    private Long portId;
    /**
     * 充电桩编号
     */
    private String pileSn;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 分成类型
     * {@link com.things.cgomp.common.core.enums.CommissionType}
     */
    private Integer commissionType;
    /**
     * 开始日期
     */
    private String beginTime;
    /**
     * 结束日期
     */
    private String endTime;
}
