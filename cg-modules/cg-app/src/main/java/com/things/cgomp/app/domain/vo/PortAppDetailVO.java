package com.things.cgomp.app.domain.vo;

import com.things.cgomp.device.api.model.vo.PortInfo;
import lombok.Data;

import java.util.List;

@Data
public class PortAppDetailVO {
    /**
     * 站点地址
     */
    private String address;
    /**
     * 停车收费信息
     */
    private String parkingInfo;
    /**
     * 停车收费类型（1停车收费 0停车免费）
     */
    private Integer feeType;
    /**
     * 充电桩ID
     */
    private Long pileId;
    /**
     * 充电桩编号
     */
    private String  pileSN;
    /**
     * 充电桩类型(0-快充，1-慢充)
     */
    private Integer pileType;
    /**
     * 设备端口列表
     */
    private List<PortInfo> portInfoList;
    /**
     * 计费标准
     */
    private List<ChargingRuleVO> chargingRule;


}
