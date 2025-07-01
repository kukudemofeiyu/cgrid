package com.things.cgomp.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 运营商分成信息
 */
@Data
public class ShareholdersSurplus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 已分配比例
     */
    private BigDecimal assignedPercent;
}
