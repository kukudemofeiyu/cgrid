package com.things.cgomp.common.device.pojo.device;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeviceInfoDTO {

    private Long brokerId;

    private String password;

    private String userName;

    private String sn;

    private Long deviceId;

    private Long productId;

    private Long parentDeviceId;

    private Long connectDeviceId;

    private String config;

    @TableField("name")
    private String name;

    private Long siteId;

    private String photos;

    private Integer productTypeId;

    /**
     * 主从系统名称
     */
    private String masterSlaveSystemName;

}
