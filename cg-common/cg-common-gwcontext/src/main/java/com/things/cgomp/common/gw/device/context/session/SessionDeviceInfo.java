package com.things.cgomp.common.gw.device.context.session;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 充电枪会话信息
 */
@Data
@Builder
public class SessionDeviceInfo {

    private Long deviceId;
    private Long productId;
    private String sn;
    private String name;

    /**
     * 充电枪设置二维码结果
     */
    private Boolean qrSetResult;

    /**
     * 设置二维码重试次数
     */
    public AtomicInteger qrSetRetryTimes;

    public Boolean isOnline;
}
