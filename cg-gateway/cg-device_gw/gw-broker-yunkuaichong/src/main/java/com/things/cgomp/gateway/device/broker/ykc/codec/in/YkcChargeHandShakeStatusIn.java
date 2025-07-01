package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class YkcChargeHandShakeStatusIn extends YkcMessageIn{

    private String orderNo;

    private String deviceNo;

    private String gunNo;

    /**
     * BMS通信协议版本号
     */
    private String bmsProtocolVersion;

    /***
     * BMS电池类型
     */
    private Integer bmsType;

    /**
     * BMS整车动力蓄电池系统额定容量
     */
    private Double bmsSystemRatedCapacity;

    /**
     * BMS整车动力蓄电池系统额定电压
     */
    private Double bmsSystemRatedCurrent;

    /**
     * BMS电池生产厂商名称
     */
    private String bmsFactoryName;

    /**
     * BMS电池组序号
     */
    private String bmsGroupSn;

    /**
     * BMS电池组生产日期年
     */
    private LocalDate bmsBirthDay;

    /**
     * BMS电池组充电次数
     */
    private Integer bmsChargeTime;

    /**
     * BMS产权标识
     */
    private Integer bmsId;


    private String vin;

    private String bmsSoftwareVer;







}
