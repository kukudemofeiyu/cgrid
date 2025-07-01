package com.things.cgomp.common.device.pojo.device;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DeviceGridDTO extends BaseEntity implements Serializable  {
    private static final long serialVersionUID = 1L;


    private Integer component;

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备编号
     */
    private String sn;

    /**
     * 站点ID
     */
    private Long siteId;

    /**
     * 设备厂商
     */
    private String productFactory;

    /**
     * 设备型号
     */
    private String productModel;

    /**
     * 设备型号
     */
    private Integer factoryId;

    private String siteName;

}


