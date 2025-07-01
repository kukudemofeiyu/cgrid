package com.things.cgomp.common.device.constants;

/**
 * @author things
 */
public class RedisKeyConstant {

    /**
     * 设备网关重启，redis key前缀
     */
    public static final String DEVICE_GATEWAY_RESTART_PREFIX = "device-gateway-restart:";

    /**
     * 设备数据服务重启，redis key前缀
     */
    public static final String DEVICE_DEVICE_DATA_RESTART_PREFIX = "device-data-restart";
    /**
     * 枪口状态 redis key
     */
    public static final String DEVICE_PORT_STATUS_LOCK_KEY = "device_port_lock:%d";
    public static final String DEVICE_PORT_STATUS_KEY = "device_port:%d";
}
