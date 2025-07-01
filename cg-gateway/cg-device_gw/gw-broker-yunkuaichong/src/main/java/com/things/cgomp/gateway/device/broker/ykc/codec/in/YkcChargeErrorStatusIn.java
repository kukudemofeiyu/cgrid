package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YkcChargeErrorStatusIn extends YkcMessageIn{

    private String orderNo;
    private String deviceNo;
    private String gunNo;

    /**
     * 接收 SPN2560=0x00 的充电机辨识报文超时 0-正常 1-超时 10-不可信状态
     */
    private Integer errStuCgSpn00;

    /**
     * 接收 SPN2560=0xAA 的充电机辨识报文超时 0-正常 1-超时 10-不可信状态
     */
    private Integer errStuCgSpnAA;

    /**
     * 接收充电机的时间同步和充电机最大输出能力报文超时
     */
    private Integer errStuCgTimeSync;

    /**
     * 接收充电机完成充电准备报文超时
     */
    private Integer errStuCgChargeReady;

    /**
     * 接收充电机充电状态报文超时
     */
    private Integer errStuCgChargeStatus;

    /**
     * 接收充电机中止充电报文超时
     */
    private Integer errStuCgChargeStop;

    /**
     * 接收充电机充电统计报文超时
     */
    private Integer errStuCgChargeStatistics;

    /**
     * BMS 其他
     */
    private Long errStuBmsOther;

    /**
     * 接收 BMS 和车辆的辨识报文超时
     */
    private Integer errStuBmsVin;

    /**
     * 接收电池充电参数报文超时
     */
    private Integer errStuBmsChargeParam;

    /**
     * 接收 BMS 完成充电准备报文超时
     */
    private Integer errStuBmsChargeReady;

    /**
     * 接收电池充电总状态报文超时
     */
    private Integer errStuBmsChargeAll;

    /**
     * 接收电池充电要求报文超时
     */
    private Integer errStuBmsChargeRequest;

    /**
     * 接收 BMS 中止充电报文超
     */
    private Integer errStuBmsStopCharge;

    /**
     * 接收 BMS 充电统计报文
     */
    private Integer errStuBmsChargeStatistics;

    /**
     * 充电机其他
     */
    private Integer errorCgOther;






}
