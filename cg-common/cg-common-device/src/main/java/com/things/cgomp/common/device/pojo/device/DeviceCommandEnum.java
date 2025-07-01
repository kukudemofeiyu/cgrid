package com.things.cgomp.common.device.pojo.device;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceCommandEnum {

    startCharge(1, "开始充电"),
    stopCharge(2, "停止充电"),
    getChargeRecord(3, "召唤交易记录"),
    setChargeFeeModel(4, "设置计费模型"),

    ;
    private Integer optCode;
    private String optName;
}
