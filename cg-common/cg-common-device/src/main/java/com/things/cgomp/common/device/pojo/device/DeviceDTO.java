package com.things.cgomp.common.device.pojo.device;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class DeviceDTO {

    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 父级ID
     */
    private Long parentId;
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
     * 设备型号
     */
    private String productModel;
    /**
     * 设备厂商
     */
    private Integer factoryId;
    /**
     * 0-桩（point） 1-枪（port）
     */
    private Integer component;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 创建者
     */
    private String  createUser;
    /**
     * 当component=1,枪口状态，0-离线 1-故障 2-空闲 3-充电 4-已插枪 5-占用
     */
    private Integer portStatus;
    /**
     * 当component=1,枪口归位状态，0-否 1-是 2-未知
     */
    private Integer homeStatus;
    /**
     * 枪口状态持续时间，小时
     */
    private BigDecimal portStatusDuration;
    /**
     * 充电桩编号
     */
    @TableField(exist = false)
    private String pileSn;
    /**
     * 子设备
     */
    @TableField(exist = false)
    private List<DeviceDTO> children;
}
