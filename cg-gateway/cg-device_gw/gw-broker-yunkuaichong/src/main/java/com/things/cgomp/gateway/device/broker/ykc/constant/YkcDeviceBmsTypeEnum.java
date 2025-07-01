package com.things.cgomp.gateway.device.broker.ykc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YkcDeviceBmsTypeEnum {


    bmsType1(0x01, "铅酸电池"),
    bmsType2(0x02, "氢电池"),
    bmsType3(0x03, "磷酸铁锂电池"),
    bmsType4(0x04, "锰酸锂电池"),
    bmsType5(0x05, "三元材料电池"),
    bmsType6(0x06, "聚合物锂离子电池"),
    bmsType7(0x07, "钛酸锂电池"),
    bmsType8(0xff, "其他"),
    ;

    private final Integer type;
    private final String typeName;

}
