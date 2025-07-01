package com.things.cgomp.device.data.constants;

/**
 * @author things
 */
public interface CacheConstant {

    String RT_KEY_PREFIX = "realData";

    /********************* redis json key**********************/
    String REDIS_DEVICE_REAL_DATA = RT_KEY_PREFIX + ":" + "%s" + ":" + "%s";
}
