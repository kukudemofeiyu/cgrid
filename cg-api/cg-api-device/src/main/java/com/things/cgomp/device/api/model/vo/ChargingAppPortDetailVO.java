package com.things.cgomp.device.api.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChargingAppPortDetailVO {
    /**
     * 站点Id
     */
    private Long siteId;
    /**
     * 充电桩ID
     */
    private Long pileId;
    /**
     * 充电桩编号
     */
    private String  pileSN;
    /**
     * 充电桩类型
     */
    private Integer pileType;

    /**
     * 规则ID
     */
    private Long ruleId;
    /**
     * 设备端口列表
     */
    private List<PortInfo> portInfoList;


}
