package com.things.cgomp.app.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 注册用户表（微信小程序用户）
 * app_user
 */
@Data
@Accessors(chain = true)
@TableName(value ="app_user_car")
public class AppUserCar implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 车牌号码
     */
    private String licensePlateNumber;

    /**
     * 车牌颜色（1蓝牌 2绿牌 3黄牌）
     */
    private Integer licensePlateColor;

    /**
     * 绑定时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bindTime;

    /**
     * 绑定状态（0已绑定 1已解绑）
     */
    private Integer bindStatus;

    /**
     * 是否默认（0非默认 1默认）
     */
    private Integer isDefault;
    /**
     * 手机号码
     */
    @TableField(exist = false)
    private String mobile;
}
