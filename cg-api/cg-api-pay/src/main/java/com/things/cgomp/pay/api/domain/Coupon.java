package com.things.cgomp.pay.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 优惠券表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 编号
     */
    private String sn;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 模板id
     */
    private Long templateId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 使用时间
     */
    private LocalDateTime userTime;

    /**
     * 0-未使用 1-已使用 2-失效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private CouponTemplate template;
}
