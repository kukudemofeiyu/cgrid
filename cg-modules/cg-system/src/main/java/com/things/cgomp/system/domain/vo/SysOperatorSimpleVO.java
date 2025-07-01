package com.things.cgomp.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 运营商简约信息
 * @author things
 * @date 2025/3/4
 */
@Data
public class SysOperatorSimpleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 运营商组织ID
     */
    private Long operatorOrgId;
    /**
     * 运营商名称
     */
    private String operatorName;
}
