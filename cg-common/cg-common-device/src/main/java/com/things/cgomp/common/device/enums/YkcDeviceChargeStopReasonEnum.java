package com.things.cgomp.common.device.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 充电停止原因
 */
@Getter
@AllArgsConstructor
public enum YkcDeviceChargeStopReasonEnum {
    /**
     * 充电完成
     */
    APP_REMOTE_CLOSE(0x40, "APP远程停止"),
    SOC100_CLOSE(0x41, "SOC达到100"),
    BALANCE_INSUFFICIENT(0x42, "充电电量满足设定条件"),
    MONEY_STOP(0x43, "充电金额满足设定条件"),
    TIME_STOP(0x44, "充电时间满足设定条件"),
    MANUALLY_STOP(0x45, "手动停止充电"),

    /**
     * 充电启动失败
     */
    CHARGE_STOP_ERROR_4A(0x4A, "充电启动失败，充电桩控制系统故障(需要重启或自动恢复)"),
    CHARGE_STOP_ERROR_4B(0x4B, "充电启动失败，控制导引断开"),
    CHARGE_STOP_ERROR_4C(0x4C, "充电启动失败，断路器跳位"),
    CHARGE_STOP_ERROR_4D(0x4D, "充电启动失败，电表通信中断"),
    CHARGE_STOP_ERROR_4E(0x4E, "充电启动失败，余额不足"),
    CHARGE_STOP_ERROR_4F(0x4F, "充电启动失败，充电模块故障"),
    CHARGE_STOP_ERROR_50(0x50, "充电启动失败，急停开入"),
    CHARGE_STOP_ERROR_51(0x51, "充电启动失败，防雷器异常"),
    CHARGE_STOP_ERROR_52(0x52, "充电启动失败，BMS 未就绪"),
    CHARGE_STOP_ERROR_53(0x53, "充电启动失败，温度异常"),
    CHARGE_STOP_ERROR_54(0x54, "充电启动失败，电池反接故障"),
    CHARGE_STOP_ERROR_55(0x55, "充电启动失败，电子锁异常"),
    CHARGE_STOP_ERROR_56(0x56, "充电启动失败，合闸失败"),
    CHARGE_STOP_ERROR_57(0x57, "充电启动失败，绝缘异常"),
    CHARGE_STOP_ERROR_58(0x58, "未知"),
    CHARGE_STOP_ERROR_59(0x59, "充电启动失败，接收 BMS 握手报文 BHM 超时"),
    CHARGE_STOP_ERROR_5A(0x5A, "充电启动失败，接收 BMS 和车辆的辨识报文超时"),
    CHARGE_STOP_ERROR_5B(0x5B, "充电启动失败，接收电池充电参数报文超时 BCP"),
    CHARGE_STOP_ERROR_5C(0x5C, "充电启动失败，接收 BMS 完成充电准备报文超时 BRO AA"),
    CHARGE_STOP_ERROR_5D(0x5D, "充电启动失败，接收电池充电总状态报文超时 BCS"),
    CHARGE_STOP_ERROR_5E(0x5E, "充电启动失败，接收电池充电要求报文超时 BCL"),
    CHARGE_STOP_ERROR_5F(0x5F, "充电启动失败，接收电池状态信息报文超时 BS"),
    CHARGE_STOP_ERROR_60(0x60, "充电启动失败，GB2015 电池在 BHM 阶段有电压不允许充"),
    CHARGE_STOP_ERROR_61(0x61, "充电启动失败，GB2015 辨识阶段在 BRO_AA 时候电池实际电压与 BCP 报文电池电压差距大于 5%"),
    CHARGE_STOP_ERROR_62(0x63, "充电启动失败，B2015 充电机在预充电阶段从 BRO_AA 变成BRO_00 状"),
    CHARGE_STOP_ERROR_63(0x63, "充电启动失败，接收主机配置报文超时"),
    CHARGE_STOP_ERROR_64(0x64, "充电启动失败，充电机未准备就绪,我们没有回 CRO AA，对应老国标"),


    /**
     * 充电异常终端
     */
    CHARGE_STOP_EXCEPTION_6A(0x6A, "充电异常中止，系统闭锁"),
    CHARGE_STOP_EXCEPTION_6B(0x6B, "充电异常中止，导引断开"),
    CHARGE_STOP_EXCEPTION_6C(0x6C, "充电异常中止，断路器跳位"),
    CHARGE_STOP_EXCEPTION_6D(0x6D, "充电异常中止，电表通信中断"),
    CHARGE_STOP_EXCEPTION_6E(0x6E, "充电异常中止，余额不足"),
    CHARGE_STOP_EXCEPTION_6F(0x6F, "充电异常中止，交流保护动作"),
    CHARGE_STOP_EXCEPTION_70(0x70, "充电异常中止，直流保护动作"),
    CHARGE_STOP_EXCEPTION_71(0x71, "充电异常中止，充电模块故障"),
    CHARGE_STOP_EXCEPTION_72(0x72, "充电异常中止，急停开入"),
    CHARGE_STOP_EXCEPTION_73(0x73, "充电异常中止，防雷器异常"),
    CHARGE_STOP_EXCEPTION_74(0x74, "充电异常中止，温度异常"),
    CHARGE_STOP_EXCEPTION_75(0x75, "充电异常中止，输出异常"),
    CHARGE_STOP_EXCEPTION_76(0x76, "充电异常中止，充电无流"),
    CHARGE_STOP_EXCEPTION_77(0x77, "充电异常中止，电子锁异常"),
    CHARGE_STOP_EXCEPTION_78(0x78, "未知"),
    CHARGE_STOP_EXCEPTION_79(0x79, "充电异常中止，总充电电压异常"),
    CHARGE_STOP_EXCEPTION_7A(0x7A, "充电异常中止，总充电电流异常"),
    CHARGE_STOP_EXCEPTION_7B(0x7B, "充电异常中止，单体充电电压异常"),
    CHARGE_STOP_EXCEPTION_7C(0x7C, "充电异常中止，电池组过温"),
    CHARGE_STOP_EXCEPTION_7D(0x7D, "充电异常中止，最高单体充电电压异常"),
    CHARGE_STOP_EXCEPTION_7E(0x7E, "充电异常中止，最高电池组过温"),
    CHARGE_STOP_EXCEPTION_7F(0x7F, "充电异常中止，BMV 单体充电电压异"),
    CHARGE_STOP_EXCEPTION_80(0x80, "充电异常中止，BMT 电池组过温"),
    CHARGE_STOP_EXCEPTION_81(0x81, "充电异常中止，电池状态异常停止充电"),
    CHARGE_STOP_EXCEPTION_82(0x82, "充电异常中止，车辆发报文禁止充电"),
    CHARGE_STOP_EXCEPTION_83(0x83, "充电异常中止，充电桩断电"),
    CHARGE_STOP_EXCEPTION_84(0x84, "充电异常中止，接收电池充电总状态报文超时"),
    CHARGE_STOP_EXCEPTION_85(0x85, "充电异常中止，接收电池充电要求报文超时"),
    CHARGE_STOP_EXCEPTION_86(0x86, "充电异常中止，接收电池状态信息报文超时"),
    CHARGE_STOP_EXCEPTION_87(0x87, "充电异常中止，接收 BMS"),
    CHARGE_STOP_EXCEPTION_88(0x88, "充电异常中止，接收 BMS 充电统计报文超时"),
    CHARGE_STOP_EXCEPTION_89(0x89, "充电异常中止，接收对侧 CCS 报文超时"),
    CHARGE_STOP_EXCEPTION_90(0x90, "未知原因停止"),




    ;

    private static final int ABNORMAL_CODE_START = CHARGE_STOP_ERROR_4A.reason;

    private final Integer reason;
    private final String reasonDesc;


    public static YkcDeviceChargeStopReasonEnum getDeviceChargeStopReasonEnum(Integer reasonCode){
        YkcDeviceChargeStopReasonEnum[] values = YkcDeviceChargeStopReasonEnum.values();
        for (YkcDeviceChargeStopReasonEnum reasonEnum:
        values) {
            if (reasonEnum.getReason().equals(reasonCode)){
                return reasonEnum;
            }
        }

        return null;
    }

    public static boolean isAbnormal(Integer reason){
        if(reason == null){
            return false;
        }
        return reason >= ABNORMAL_CODE_START;
    }
}
