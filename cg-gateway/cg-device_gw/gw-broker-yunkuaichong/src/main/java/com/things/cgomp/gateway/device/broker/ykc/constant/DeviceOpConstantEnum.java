package com.things.cgomp.gateway.device.broker.ykc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 云快充帧类型标志
 */
@Getter
@AllArgsConstructor
public enum DeviceOpConstantEnum {
    /**
     * 充电桩登录认证   充电桩->运营平台
     */
    CONNECT(0x0001, "充电桩登录认证"),
    /**
     * 登录认证应答 运营平台->充电桩
     */
    CONNECT_RESP(0x0002, "登录认证应答"),

    /**
     * 充电桩心跳包 充电桩->运营平台
     */
    HEARTBEAT(0x0003, "充电桩心跳包"),
    /**
     * 心跳包应答 运营平台->充电桩
     */
    HEARTBEAT_RESP(0x0004, "心跳包应答"),

    /**
     * 计费模型验证请求  充电桩->运营平台
     */
    CHARGE_STANDARD_CHECK(0x0005, "计费模型验证请求"),
    /**
     * 计费模型验证请求响应  运营平台->充电桩
     */
    CHARGE_STANDARD_CHECK_RESP(0x0006, "计费模型验证请求响应"),

    /**
     * 充电桩计费模型请求
     */
    CHARGE_STANDARD_SYNC(0x0009, "充电桩计费模型请求"),
    /**
     *计费模型请求应答
     */
    CHARGE_STANDARD_SYNC_RESP(0x000A, "计费模型请求应答"),

    /**
     * 读取实时监测数据
     */
    DEVICE_STATUS_READ(0x0012, "读取实时监测数据"),


    /**
     * 上传实时监测数据
     */
    DEVICE_STATUS_REPORT(0x0013, "上传实时监测数据"),

    /**
     * 充电结束
     */
    DEVICE_CHARGE_END(0x0019, "充电结束报文"),

    /**
     * 二维码设置 运营平台->充电桩
     */
    UPDATE_DEVICE_QR(0x005B, "二维码设置"),
    /**
     * 二维码设置应答 充电桩->运营平台
     */
    UPDATE_DEVICE_QR_RESP(0x005A, "二维码设置应答"),

    /**
     * 运营平台远程控制启机 运营平台->充电桩
     */
    START_CHARGE(0x00A8, "运营平台远程控制启机"),
    /**
     * 运营平台远程控制启机响应 充电桩->运营平台
     */
    START_CHARGE_RESP(0x00A7, "运营平台远程控制启机响应"),

    /**
     * 充电桩上报 vin 码
     */
    DEVICE_VIN_REPORT(0x00A9, "充电桩上报vin码"),
    /**
     * 充电桩上报 vin 码 回复
     */
    DEVICE_VIN_REPORT_RESP(0x00AA, "充电桩上报vin码回复"),

    /**
     * 运营平台远程停机 运营平台->充电桩
     */
    STOP_CHARGE(0x0036, "运营平台远程停机"),
    /**
     * 远程停机命令回复 充电桩->运营平台
     */
    STOP_CHARGE_RESP(0x0035, "远程停机命令回复"),

    /**
     * 交易记录
     */
    CHARGE_RECORD(0x003D, "交易记录"),
    /**
     * 交易记录回复
     */
    CHARGE_RECORD_RESP(0x0040, "交易记录回复"),

    /**
     * 同步时间
     */
    DEVICE_TIME_SYNC(0x0056, "同步时间"),
    /**
     * 同步时间
     */
    DEVICE_TIME_SYNC_RESP(0x0055, "同步时间响应"),

    /**
     * 充电握手
     */
    CHARGE_HANDSHAKE(0X0015, "充电握手"),

    /**
     * 参数配置 充电桩->运营平台
     */
    CHARGE_CONFIG_SETTING(0X0017, "参数配置"),

    /**
     * 错误报文 充电桩->运营平台
     */
    CHARGE_ERROR_STATUS(0X001B, "错误报文"),

    /**
     * 充电阶段BMS中止 充电桩->运营平台
     */
    CHARGE_BMS_STOP_STATUS(0X001D, "充电阶段BMS中止"),

    /**
     * 充电阶段充电机中止 充电桩->运营平台
     */
    CHARGE_CG_STOP_STATUS(0X0021, "充电阶段充电机中止"),

    /**
     * 充电过程 BMS 需求、充电机输出 充电桩->运营平台
     */
    CHARGE_OUTPUT_STATUS(0X0023, "充电过程BMS需求充电机输出"),

    /**
     * 充电过程 BMS 信息 充电桩->运营平台
     */
    CHARGE_BMS_STATUS(0X0025, "充电过程BMS信息"),

    /**
     * 交易记录召唤 运营平台->充电桩
     */
    GET_CHARGE_RECORD(0X004D, "交易记录召唤"),
    /**
     * 交易记录召唤回复 充电桩 ->运营平台
     */
    GET_CHARGE_RECORD_RESP(0X004C, "交易记录召唤回复"),

    /**
     * 计费模型设置 运营平台->充电桩
     */
    SET_CHARGE_STANDARD_CHECK(0X0058, "计费模型设置"),
    /**
     * 计费模型设置应答 充电桩 ->运营平台
     */
    SET_CHARGE_STANDARD_CHECK_RESP(0X0057, "计费模型设置应答"),

    /**
     * 设备故障上送
     */
    DEVICE_ALARM_REPORT(0X0050,"设备故障上送"),
    /**
     * 设备故障上送确认
     */
    DEVICE_ALARM_REPORT_RESP(0X0049,"设备故障上送确认"),

    /**
     * 设备故障复位上送
     */
    DEVICE_ALARM_RECOVER(0X004B,"设备故障复位上送"),
    /**
     * 设备故障复位上送确认
     */
    DEVICE_ALARM_RECOVER_RESP(0X004A,"设备故障复位上送确认"),



    ;
    /**
     * 操作代码
     */
    private final Integer opCode;
    /**
     * 操作名称
     */
    private final String opName;



    public static DeviceOpConstantEnum getOptEnum(Integer opCode){
        DeviceOpConstantEnum[] values = DeviceOpConstantEnum.values();
        for (DeviceOpConstantEnum opConstantEnum:
        values) {
            if(opConstantEnum.getOpCode().equals(opCode)){
                return opConstantEnum;
            }
        }

        return null;
    }

}
