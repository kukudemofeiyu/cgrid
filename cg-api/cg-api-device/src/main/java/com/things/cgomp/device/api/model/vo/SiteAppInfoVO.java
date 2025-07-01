package com.things.cgomp.device.api.model.vo;

import lombok.Data;

import java.util.List;

/**
 * ChargingPileVO 类用于表示充电桩的相关信息。
 * 该类包含了充电桩所在的地理位置信息以及充电站的名称。
 */
@Data
public class SiteAppInfoVO extends SiteAppVO {
    /**
     * 充电桩图片（多个图片用逗号分开）
     */
    private String photos;

    /**
     * 默认车牌
     */
    private String defaultCar;
    /**
     * 充电桩端口信息
     */
    private List<ChargingPile> list;

}
