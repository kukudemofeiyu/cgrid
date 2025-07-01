package com.things.cgomp.common.mq.constants;

/**
 *  Rocketmq 主题定义
 *  @author things
 */
public class MQTopics {

    /** 状态主题 **/
    public static final String STATUS = "status";
    /** 节点主题 **/
    public static final String NODE = "node";
    /** 设备数据主题 **/
    public static final String DEVICE_DATA = "device-data";
    /** 订单主题 **/
    public static final String ORDER = "order";
    /** app主题 **/
    public static final String APP = "app";
    /** 设备交互日志主题 **/
    public static final String CMD_lOG = "device-cmd-log";
    /** 确认消息主题，用于设备网关与系统消息确认 **/
    public static final String CONFIRM_MESSAGE = "confirm";
    /** 告警主题 **/
    public static final String ALARM = "alarm";
    /** 设备充电数据主题 **/
    public static final String DEVICE_CHARGE_DATA = "device-charge-data";
    /** 设备拓展信息主题 **/
    public static final String DEVICE_EXPAND = "device-expand";

    /**
     * 消费组
     */
    public static class Group {
        public static final String GROUP = "${rocketmq.consumer.group}-";

        /** 状态主题消费组 **/
        public static final String STATUS = GROUP + MQTopics.STATUS;
        /** 节点主题消费组 **/
        public static final String NODE = GROUP + MQTopics.NODE;
        /** 设备数据主题消费组 **/
        public static final String DEVICE_DATA = GROUP + MQTopics.DEVICE_DATA;
        /** 订单主题消费组 **/
        public static final String ORDER = GROUP + MQTopics.ORDER;
        /** APP主题消费组 **/
        public static final String APP = GROUP + MQTopics.APP;
        /** APP主题消费组 **/
        public static final String CMD_lOG = GROUP + MQTopics.CMD_lOG;
        /** 确认消息主题消费组 **/
        public static final String CONFIRM_MESSAGE = GROUP + MQTopics.CONFIRM_MESSAGE;
        /** 确认消息主题消费组 **/
        public static final String ALARM = GROUP + MQTopics.ALARM;
        /** 设备充电数据主题消费组 **/
        public static final String DEVICE_CHARGE_DATA = GROUP + MQTopics.DEVICE_CHARGE_DATA;
        /** 设备拓展信息主题消费组 **/
        public static final String DEVICE_EXPAND = GROUP + MQTopics.DEVICE_EXPAND;
    }

    /**
     * 消费主题标签
     */
    public static class Tag {

        /** 上线消息TAG **/
        public static final String ONLINE = "online";
        /** 离线消息TAG **/
        public static final String OFFLINE = "offline";
        /** 会话更新消息TAG **/
        public static final String SESSION_SYNC = "sessionSync";
        /** 结束充电TAG **/
        public static final String ORDER_END_CHARGING = "orderEndCharging";
        /** 交易记录确认-请求TAG **/
        public static final String TRADING_RECORD_CONFIRM_REQ = "tradingRecordConfirmReq";
        /** 交易记录确认-回复TAG **/
        public static final String TRADING_RECORD_CONFIRM_RESP = "tradingRecordConfirmRes";
        /** 订单交易记录召唤 **/
        public static final String TRADING_RECORD_CALL = "tradingRecordCall";
        /** 订单支付成功TAG **/
        public static final String ORDER_PAY_SUCCESS = "orderPaySuccess";
        /** 拔枪-请求TAG **/
        public static final String DRAW_GUN_REQ = "drawGunReq";
        /** 订单金额消息TAG **/
        public static final String ORDER_AMOUNT = "orderAmount";
        /** 充电信息 **/
        public static final String CHARGE_INFO = "chargeInfo";
        /** 充电结算 **/
        public static final String CHARGE_SETTLEMENT = "chargeSettlement";
        /** 计费规则更新通知 */
        public static final String CONFIRM_PAY_RULE_UPDATE = "updatePayRule";
        /** 通知app结束充电 **/
        public static final String APP_END_CHARGE = "appEndCharge";
        /** BMS需求与充电机输出 **/
        public static final String DEVICE_CHARGE = "deviceCharge";
        /** 充电握手数据 **/
        public static final String DEVICE_CHARGE_HANDSHAKE = "deviceChargeHandshake";
    }
}
