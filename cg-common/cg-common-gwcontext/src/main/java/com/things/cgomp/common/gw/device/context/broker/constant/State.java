package com.things.cgomp.common.gw.device.context.broker.constant;


public enum State {

    initialize(1, "初始化"), initializeComplete(2, "初始化完成"),
    runIng(3, "运行中"), stop(4, "停止");


    public int getValue() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
    Integer val;
    String desc;

    State(Integer val, String desc) {
        this.val = val;
        this.desc = desc;
    }
}
