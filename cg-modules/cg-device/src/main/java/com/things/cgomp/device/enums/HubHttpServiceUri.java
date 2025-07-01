package com.things.cgomp.device.enums;


public class HubHttpServiceUri {

    /**
     * 推送
     */
    public static final String PUSH = "push/command/device";

    /*
    远程升级
     */
    public static final String PUSH_OTA = "push/ota/to/device";

    /**
     * 获取设备本地日志
     */
    public static final String GET_LOG = "push/get/device/log";

    /**
     * 发送调试报文
     */
    public static final String DEBUG = "push/debug/device";

    /**
     * 销毁设备会话
     */
    public static final String DESTROY_SESSION = "push/session/device/destroy";

}
