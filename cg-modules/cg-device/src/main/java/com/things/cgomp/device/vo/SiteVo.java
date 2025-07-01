package com.things.cgomp.device.vo;

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
public class SiteVo implements Serializable {

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
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 站点图片 多个用,隔开
     */
    private String photos;

    /**
     * 国家编码
     */
    private Long countryCode;

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
     * 站点地址
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 停车收费信息
     */
    private String parkingInfo;

    /**
     * 停车收费类型（1停车收费 0停车免费）
     */
    private Integer feeType;

    /**
     * 是否可以发票（1可以 0不可以）
     */
    private Integer isInvoice;

    /**
     * 是否可以预约(1支持 0不支持)
     */
    private Integer isReservation;

    /**
     * 锁桩时间（分钟）
     */
    private Integer pileLockTime;

    /**
     * 锁单保证金(元)
     */
    private BigDecimal lockDeposit;

    /**
     * 配套设施(0免费WiFi 1便利店 2洗车 3厕所) 可多选 逗号分开
     */
    private String support;

    /**
     * 负责人姓名
     */
    private String directorName;

    /**
     * 负责人手机
     */
    private String directorPhone;

    /**
     * 备注
     */
    private String remarks;

    /**
     *运营状态：0-停业 1-在营
     */
    private Integer operateStatus;

    /**
     * 小程序是否显示（0隐藏 1显示）
     */
    private Integer appDisplay;

    /**
     * 充电站类型（0 公共充电站 1商业充电站 2居住充电站 3高速公路充电站 4智能充电站）
     */
    private Integer type;

    /**
     * 站点运营开始时间
     */
    private String operateStartTime;

    /**
     * 站点运营结束时间
     */
    private String operateEndTime;

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
     * 运营商名称
     */
    private String operatorName;

    /**
     * 充电桩总数
     */
    private Integer pileNum;

    /**
     * 充电枪总数
     */
    private Integer portTotalNum;

    /**
     * 使用中枪口数量
     */
    private Integer portUsingNum;

    /**
     * 空闲枪口数量
     */
    private Integer portFreeNum;

    /**
     * 故障枪口数量
     */
    private Integer portFaultNum;
}
