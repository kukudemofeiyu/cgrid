package com.things.cgomp.order.api.dto;

import com.things.cgomp.common.core.utils.DateUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class AddOrderDTO {

    /**
     * 订单编号
     */
    private String sn;

    /**
     * 订单类型：0-充电订单 1-预约订单 2-占位订单
     */
    private Integer orderType;

    /**
     * 0-微信 1-卡支付 2-账户余额
     */
    private Integer payType;

    /**
     * 订单来源：1-微信小程序，2-H5，3-卡
     */
    private Integer orderSource;

    /**
     * 计费类型，0-按小时计费（取决于用户选择,默认） 1-按度数计费（取决于 用户选择）
     */
    private Integer billType;

    /**
     * 充电口ID
     */
    private Long portId;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 设备类型 2:二轮车 4:四轮车
     */
    private Integer deviceType;

    /**
     * 对应于app_user主键
     */
    private Long userId;

    /**
     * 车牌号码
     */
    private String licensePlateNumber;

    /**
     * 手机号
     */
    private String phone;

    /**
     * VIN码
     */
    private String vin;

    /**
     * 插枪时间
     */
    private Long insertTime;

    public LocalDateTime buildInsertTime(){
        if(insertTime == null){
            return LocalDateTime.now();
        }

        return DateUtils.toLocalDateTime(insertTime);
    }

}
