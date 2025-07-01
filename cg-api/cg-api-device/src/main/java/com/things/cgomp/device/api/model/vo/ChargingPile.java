package com.things.cgomp.device.api.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChargingPile {
    /**
     * name（桩名称）
     */
    private String name;
    /**
     * 桩类型（0-快充，1-慢充）
     */
    private Integer pileType;
    /**
     * 规则ID
     */
    private Long ruleId;
    /**
     * 枪设备集合
     */
    private List<ChargingPort> ports;
}
