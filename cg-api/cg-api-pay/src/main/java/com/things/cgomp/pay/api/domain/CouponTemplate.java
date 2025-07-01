package com.things.cgomp.pay.api.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.pay.api.enums.CouponTypeEnum;
import com.things.cgomp.pay.api.enums.UserTimeTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 优惠券模板表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_coupon_template")
public class CouponTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 0-暂停使用 1-正常
     */
    private Integer status;

    /**
     * 优惠券类型：0-现金券 1-折扣券
     */
    private Integer couponType;

    /**
     * 面额
     */
    private BigDecimal faceValue;

    /**
     * 折扣比例(%)
     */
    private Integer rate;

    /**
     * 总张数
     */
    private Integer totalNumber;

    /**
     * 可用张数
     */
    private Integer availableNumber;

    /**
     * 作废张数
     */
    private Integer cancelNumber;

    /**
     * 可抵扣费用(0-服务费 1-总费用)
     */
    private Integer deductionType;

    /**
     * 满足总费用额度
     */
    private BigDecimal feeLimit;

    /**
     * 使用时间类型(0-相对时间 1-绝对时间)
     */
    private Integer useTimeType;

    /**
     * 可使用天数
     */
    private Integer availableDays;

    /**
     * 使用开始时间
     */
    private LocalDate startTime;

    /**
     * 使用结束时间
     */
    private LocalDate endTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 可用站点维度(0-全部站点 1-部分站点)
     */
    private Integer siteDimension;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 站点id列表
     */
    @TableField(exist = false)
    private List<Long> siteIds;

    public LocalDateTime buildStartTime() {
        if (UserTimeTypeEnum.RELATIVE_TIME.getType().equals(useTimeType)) {
            return LocalDateTime.now();
        }

        return startTime.atStartOfDay();
    }

    public LocalDateTime buildEndTime() {
        if (UserTimeTypeEnum.RELATIVE_TIME.getType().equals(useTimeType)) {
            if (availableDays == null) {
                return LocalDateTime.now();
            }

            return LocalDateTime.now().plusDays(availableDays);
        }

        return DateUtils.endOfDay(endTime);
    }

    public BigDecimal buildDiscount() {
        if (CouponTypeEnum.CASH.getType().equals(couponType)) {
            return faceValue;
        }

        return new BigDecimal(rate);
    }

}
