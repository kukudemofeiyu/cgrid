package com.things.cgomp.device.api.model.vo;

import lombok.Data;

@Data
public class PortInfo {
    /**
     * 设备端口ID
     */
    private Long portId;
    /**
     * 设备端口编号
     */
    private String portSN;
    /**
     * 当component=1,枪口状态，0-离线 1-故障 2-空闲 3-充电 4-已插枪 5-占用
     */
    private Integer portStatus;
    /**
     * 设备端口名称
     */
    private String portName;
}
