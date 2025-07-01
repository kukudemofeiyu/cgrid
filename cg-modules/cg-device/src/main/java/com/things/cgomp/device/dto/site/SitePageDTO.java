package com.things.cgomp.device.dto.site;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SitePageDTO extends PageDTO {

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 站点名称
     */
    private String name;

    /**
     *运营状态：0-停业 1-在营
     */
    private Integer operateStatus;

    /**
     * 省份编码
     */
    private Long provinceCode;

    /**
     * 城市编码
     */
    private Long cityCode;

    /**
     * 区编码
     */
    private Long districtCode;

    /**
     * 小程序是否显示（0隐藏 1显示）
     */
    private Integer appDisplay;

}
