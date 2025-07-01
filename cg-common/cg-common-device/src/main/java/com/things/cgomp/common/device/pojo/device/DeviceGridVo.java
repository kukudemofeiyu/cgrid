package com.things.cgomp.common.device.pojo.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class DeviceGridVo {

    private Long deviceId;

    /**
     * 设备编号
     */
    private String sn;

    /**
     * 别名sn,用于设备通信
     */
    private String aliasSn;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 充电类型，0-快充，1-慢充
     */
    private Integer chargeType;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 运营商名称
     */
    private String operatorName;

    /**
     * 状态，0-禁用，1-启用
     */
    private Integer status;

    /**
     * 网络状态：0-离线 1-在线
     */
    private Integer netStatus;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 设备厂商
     */
    private String productFactory;


    /**
     * 设备厂商
     */
    private Integer factoryId;

    /**
     * 设备型号
     */
    private String productModel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建者
     */
    private String  createUser;

    /**
     * 是否收费，0-否，1-是
     */
    private Integer isFree;

    /**
     * 总耗电量
     */
    private String totalElec;

    /**
     * 计费规则id
     */
    private Long payRuleId;

}
