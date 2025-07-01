package com.things.cgomp.pay.dto.coupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.enums.ChargeDimensionEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CouponActivityConfigDTO {

    /**
     * 0-度数维度 1-金额维度
     */
    private Integer chargeDimension;

    /**
     * 满xx开始赠送
     */
    private BigDecimal startValue;

    /**
     * 优惠券列表
     */
    private List<ActivityCouponDTO> coupons;

    /**
     * 1-不限制 2-设置次数（次/人/天） 3-设置次数（次/人/活动周期）
     */
    private Integer receiveLimitType;

    /**
     * 设置次数
     */
    private Integer receiveLimit;

    public List<Long> buildTemplateIds() {
        if (CollectionUtils.isEmpty(coupons)) {
            return new ArrayList<>();
        }

        return coupons.stream()
                .map(ActivityCouponDTO::getTemplateId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public CouponActivityConfigDTO filterCoupons(List<Long> templateIds){
       if(CollectionUtils.isEmpty(coupons)){
           return this;
       }

        coupons = buildCoupons(templateIds);
        return this;
    }

    @NotNull
    private List<ActivityCouponDTO> buildCoupons(List<Long> templateIds) {
        Map<Long, ActivityCouponDTO> couponMap = buildCouponMap();

        return templateIds.stream()
                .map(couponMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Map<Long, ActivityCouponDTO> buildCouponMap() {
        if (coupons == null) {
            return new HashMap<>();
        }

        return coupons.stream()
                .collect(Collectors.toMap(
                        ActivityCouponDTO::getTemplateId,
                        a -> a,
                        (a, b) -> a
                ));
    }

    public boolean meetCondition(
            OrderInfo orderInfo
    ) {
        if (ChargeDimensionEnum.ELECTRICITY.getType().equals(chargeDimension)) {
            return orderInfo.getConsumeElectricity() != null
                    && startValue != null
                    && orderInfo.getConsumeElectricity().compareTo(startValue) >= 0;
        }

        if (ChargeDimensionEnum.MONEY.getType().equals(chargeDimension)) {
            return orderInfo.getPayAmount() != null
                    && startValue != null
                    && orderInfo.getPayAmount().compareTo(startValue) >= 0;
        }

        return false;
    }

}
