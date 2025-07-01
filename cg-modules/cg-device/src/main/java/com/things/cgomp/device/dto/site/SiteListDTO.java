package com.things.cgomp.device.dto.site;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SiteListDTO extends BaseEntity {

    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 设备类型 4 四轮车 2 二轮车
     */
    private Integer deviceType;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 站点名称
     */
    private String siteName;
    /**
     * 当前位置经度
     */
    private Float lng;
    /**
     * 当前位置纬度
     */
    private Float lat;

    /**
     * 站点地址
     */
    private String address;

    /**
     * 推荐站点 0 空闲较多 1 距离优先
     */
    private Integer recommendSitesStatus;

    /**
     * 充电桩类型 0 慢充 1 快充
     */
    private Integer pileType;

    /**
     * 配套设施 1 免费WIFI 2 便利店 3 洗车 4 厕所
     */
    private List<String> supports;

    /**
     * 停车费 0 停车收费 1 停车免费
     */
    private Integer parkCarStatus;

    /**
     * 是否可以开发票,0 不可以 1 可以
     */
    private Integer receiptStatus;
}
