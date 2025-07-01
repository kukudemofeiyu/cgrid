package com.things.cgomp.device.vo.device;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 设备树精简信息
 * @author things
 */
@Data
@Accessors(chain = true)
public class DeviceSimpleTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充设备ID
     */
    private Long deviceId;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备编码
     */
    private String sn;
    /**
     * 网络状态
     */
    private Integer netStatus;
    /**
     * 枪口状态
     */
    private Integer portStatus;
    /**
     * 0-桩（point） 1-枪（port）
     */
    private Integer component;
    /**
     * 子设备列表
     */
    private List<DeviceSimpleTreeVO> children;
}
