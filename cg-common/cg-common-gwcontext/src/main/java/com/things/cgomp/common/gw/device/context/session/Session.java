package com.things.cgomp.common.gw.device.context.session;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 会话信息
 *
 * @param <channel>
 */
@Data
public class Session<channel> implements Serializable {
    private channel channel;  //链接通道
    private String sessionId;//会话ID
    private String clientIp;//链接IP
    private Long deviceId; //设备ID
    private Long productId;
    private String sn; //设备唯一码
    private Long startTime;//会话开始时间
    private Long lastActiveTime; //会话最后活跃时间
    private Long lastSyncSessionTime;//最后一次状态同步(与iot服务端)时间
    private transient SessionState sessionState; //状态
    private volatile Map<String /**设备的sn */, SessionDeviceInfo> childDevices; //子设备
    private volatile boolean isAuth = false;
    private AtomicInteger msgId;

    /**
     * 异常离线时间(分钟)，当最后活跃时间+异常离线时间>当前时间，判定离线
     */
    private Integer abnormalOffLineTime;

    public Session() {
    }

    public Session(channel channel, String sessionId, String clientIP) {
        this.channel = channel;
        this.sessionId = sessionId;
        this.clientIp = clientIP;
    }

    public void initDeviceInfoMap() {
        if (null == childDevices) {
            synchronized (this) {
                if (childDevices == null) {
                    childDevices = new ConcurrentHashMap<>();
                }
            }
        }
    }

    public synchronized int nexMsgId() {
        if (msgId == null) {
            msgId = new AtomicInteger(0);
        }
        if (msgId.intValue() >= 0xffff)
            msgId.set(0);
        return msgId.incrementAndGet();
    }

    public SessionDeviceInfo getChildDeviceInfo(String nodeCode) {
        if (null == childDevices) {
            return null;
        }
        return childDevices.get(nodeCode);
    }

    public void addChildDeviceInfo(String nodeCode, SessionDeviceInfo cDeviceInfo) {
        initDeviceInfoMap();
        childDevices.put(nodeCode, cDeviceInfo);
    }

    public boolean isActive(Integer keepSessionTime /** 单位秒**/) {
        return (System.currentTimeMillis() - lastActiveTime) < (keepSessionTime * 1000);
    }
}
