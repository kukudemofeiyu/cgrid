package com.things.cgomp.device.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 充电桩厂商表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-06
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("device_product_model")
public class ProductFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 产品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 厂商名称
     */
    private String name;

    private String tellPerson;

    private String tellPhone;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long createBy;

    private Long operatorId;

}
