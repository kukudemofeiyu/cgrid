package com.things.cgomp.app.domain.vo;

import lombok.Data;

@Data
public class PaymentTypeDTO {
    /**
     * 支付类型ID
     */
    private Long id;
    /**
     * 支付类型代码
     */
    private String typeCode;

    /**
     * 支付类型名称
     */
    private String typeName;

    /**
     * 支付类型描述
     */
    private String description;

    /**
     * 系统是否默认（0：非默认，1：默认）
     */
    private Integer isDefault;

}
