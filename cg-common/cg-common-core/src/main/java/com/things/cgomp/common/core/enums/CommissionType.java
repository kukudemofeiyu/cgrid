package com.things.cgomp.common.core.enums;

import lombok.Getter;

/**
 * 分成类型
 */
@Getter
public enum CommissionType {

    ELECTRICITY_BILL(1, "电费"),
    SERVICE_BILL(2, "服务费"),
    ELECTRICITY_AND_SERVICE_BILL(3, "电费+服务费"),
    ;

    private final Integer type;
    private final String desc;

    CommissionType(Integer type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public static CommissionType getByType(Integer type){
        CommissionType[] values = CommissionType.values();
        for (CommissionType value : values) {
            if(value.type.equals(type)){
                return value;
            }
        }
        return null;
    }
}
