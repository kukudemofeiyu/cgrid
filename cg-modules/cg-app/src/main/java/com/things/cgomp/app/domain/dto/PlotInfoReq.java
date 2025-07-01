package com.things.cgomp.app.domain.dto;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;

import java.util.List;
@Data
public class PlotInfoReq extends PageDTO {
    /**
     * 设备类型 4 四轮车 2 二轮车
     */
    private Integer deviceType;
    /**
     * 当前位置经度
     */
    private Float lng;
    /**
     * 当前位置纬度
     */
    private Float lat;

    /**
     * 排序规则 1 距离 2 价格 3 智能排序(先距离,后价格)
     */
    private String sortType;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 站点名称
     */
    private String siteName;

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
     * 配套设施 0 免费WIFI 1 便利店 2 洗车 3 厕所
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
