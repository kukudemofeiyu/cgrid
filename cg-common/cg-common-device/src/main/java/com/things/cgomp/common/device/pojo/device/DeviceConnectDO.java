package com.things.cgomp.common.device.pojo.device;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeviceConnectDO {

    private Long deviceId;

    /**
     * 设备编号
     */
    private String sn;

    /**
     * 别名sn,用于设备通信
     */
    private String aliasSn;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 网络状态：0-离线 1-在线
     */
    private Integer netStatus;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 设备厂商
     */
    private String productFactory;

    /**
     * 设备型号
     */
    private String productModel;


    private String nodeId;

    private Integer brokerId;




}
