package com.things.cgomp.pay.vo.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.pay.api.enums.DeductionTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class CouponTemplateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
     * 优惠券类型：0-现金券 1-折扣券
     */
    private Integer couponType;

    /**
     * 可抵扣费用(0-服务费 1-总费用)
     */
    private Integer deductionType;

    /**
     * 面额
     */
    private BigDecimal faceValue;

    /**
     * 折扣比例(%)
     */
    private Integer rate;

    /**
     * 满足总费用额度
     */
    private BigDecimal feeLimit;

    private String useCondition;

    /**
     * 0-暂停使用 1-正常
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改人名称
     */
    private Long updateBy;

    /**
     * 修改人
     */
    private String updateByName;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String buildUseCondition(){
        String deductionTypeDescription = DeductionTypeEnum.getDescription(
                deductionType
        );

        if(StringUtils.isBlank(deductionTypeDescription) || feeLimit == null){
            return null;
        }

        return deductionTypeDescription
                + "满"
                + feeLimit
                + "元";
    }

    public CouponTemplateVo setUseCondition() {
        useCondition = buildUseCondition();
        return this;
    }

}
