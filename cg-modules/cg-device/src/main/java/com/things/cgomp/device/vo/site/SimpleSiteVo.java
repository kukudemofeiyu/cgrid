package com.things.cgomp.device.vo.site;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 站点表
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@Getter
@Setter
@Accessors(chain = true)
public class SimpleSiteVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 站点id
     */
    private Long id;

    /**
     * 站点唯一序列号
     */
    private String sn;

    /**
     * 站点名称
     */
    private String name;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

}
