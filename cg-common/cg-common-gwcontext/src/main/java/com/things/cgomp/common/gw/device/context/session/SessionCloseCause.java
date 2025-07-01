package com.things.cgomp.common.gw.device.context.session;

public enum SessionCloseCause {
    DECODER(1, "粘拆包异常"),
    READ(2, "读取数据异常"),
    INACITVE(3, "客户端不活跃,主动断开"),
    SERVER_IO_ACITVE(4, "客户端不活跃,服务器主动断开"),
    GW_NOTICE_DOWON_LINE(5, "网关设备通知下线"),
    DisConnect_DOWON_LINE(6, "DisConnect请求命命令"),
    MANAGER_DOWN_LINE(7, "管理员强制下线"),
    FETCH_DATA_IS_NULL(8, "主动请求,但获取不到数据"),
    CHILD_DEVICE_UNBINDING(9, "子设备解除绑定，断开连接"),
    PING_FAIL(10, "无法访问设备"),
    PLATFORM_REPORT_OFFLINE(11, "平台报告离线"),
    ;

    private int type;
    private String desc;

    private SessionCloseCause(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
