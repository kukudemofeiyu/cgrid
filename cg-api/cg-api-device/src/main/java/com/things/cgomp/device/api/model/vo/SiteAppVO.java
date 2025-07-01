package com.things.cgomp.device.api.model.vo;

import lombok.Data;

@Data
public class SiteAppVO {
    /**
     * 站点ID
     */
    private Long id;

    /**
     * 充电站名称
     */
    private String siteName;

    /**
     * 停车服务信息
     */
    private String parkCarInfo;

    /**
     * 经度
     */
    private float lng;

    /**
     * 维度
     */
    private float lat;
    /**
     *当前充电时段
     */
    private String chargeTime;
    /**
     * 充电总费用
     */
    private float chargeCost;

    /**
     * 充电费用
     */
    private float price;

    /**
     * 充电服务费
     */
    private float servicePrice;



    /**
     * 总数量
     */
    private Integer totalNum=0;

    /**
     * 慢充数量
     */
    private Integer totalSlowNum=0;

    /**
     * 快充数量
     */
    private Integer totalFastNum=0;
    /**
     * 闲时慢充数量
     */
    private Integer idleSlowNum=0;

    /**
     * 闲时快充数量
     */
    private Integer idleFastNum=0;

//    /**
//     * 平台类型 4:四轮车  2:二轮车
//     */
//    private Integer deviceType;

    /**
     * 地址信息
     */
    private String address;

    /**
     * 省份
     */
    private String provinceName;

    /**
     * 城市
     */
    private String cityName;

    /**
     * 区
     */
    private String districtName;

    /**
     * 距离
     */
    private double distance;


    /**
     * 配套设施(0免费WiFi 1便利店 2洗车 3厕所) 可多选 逗号分开
     */
    private String support;

    /**
     * 停车收费类型（1停车收费 0停车免费）
     */
    private Integer feeType;

    /**
     * 是否可以发票（1可以 0不可以）
     */
    private Integer isInvoice;

}
