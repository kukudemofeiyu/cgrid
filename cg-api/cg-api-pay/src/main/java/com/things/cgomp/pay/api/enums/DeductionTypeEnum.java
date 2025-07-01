package com.things.cgomp.pay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeductionTypeEnum {
    SERVICE_FEE(0, "服务费"),
    TOTAL_COST(1, "总费用"),
    ;
    private final Integer type;

    private final String description;

    private static DeductionTypeEnum getEnum(Integer type) {
        if (type == null) {
            return null;
        }

        for (DeductionTypeEnum typeEnum : DeductionTypeEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static String getDescription(
            Integer type
    ) {
        DeductionTypeEnum deductionTypeEnum = DeductionTypeEnum.getEnum(type);
        if(type == null){
            return null;
        }

        return deductionTypeEnum.getDescription();
    }

    public static Integer getDeductionType(Integer activityType) {
        if (SiteActivityTypeEnum.TOTAL_COST_TYPES.contains(activityType)) {
            return DeductionTypeEnum.TOTAL_COST.getType();
        }

        return DeductionTypeEnum.SERVICE_FEE.getType();
    }

}