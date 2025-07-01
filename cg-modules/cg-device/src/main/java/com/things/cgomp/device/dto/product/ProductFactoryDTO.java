package com.things.cgomp.device.dto.product;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProductFactoryDTO extends BaseEntity {

    private Integer id;

    /**
     * 产品型号名称
     */
    private String model;

    /**
     * 厂家id
     */
    private Long factoryId;

    /**
     * 描述
     */
    private String desc;

    /**
     * 运营商
     */
    private Long operatorId;

}
