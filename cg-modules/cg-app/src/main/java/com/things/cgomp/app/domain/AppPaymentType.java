package com.things.cgomp.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 支付类型表
 * @TableName app_payment_type
 */
@TableName(value ="app_payment_type")
@Data
public class AppPaymentType implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 支付类型代码
     */
    private String typeCode;

    /**
     * 支付类型名称
     */
    private String typeName;

    /**
     * 支付类型描述
     */
    private String description;

    /**
     * 是否启用（0：禁用，1：启用）
     */
    private Integer isEnabled;

    /**
     * 系统是否默认（0：非默认，1：默认）
     */
    private Integer isDefault;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}