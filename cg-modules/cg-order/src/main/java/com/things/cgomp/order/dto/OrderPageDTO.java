package com.things.cgomp.order.dto;

import cn.hutool.core.date.DateUtil;
import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OrderPageDTO extends PageDTO {

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 订单编号
     */
    private String sn;

    /**
     * 交易流水号
     */
    private String tradeSn;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 计费类型：0-按小时计费 1-按度数计费
     */
    private Integer billType;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 充电桩编号
     */
    private String pileSn;

    /**
     * 0-微信 1-卡支付 2-账户余额
     */
    private Integer payType;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 订单状态（聚合而成）：1-进行中 2-已完成
     */
    private Integer orderState;

    /**
     * 0-未支付 1-已支付
     */
    private Integer payStatus;

    /**
     * 退款状态：0-未退款 1-退款
     */
    private Integer refundStatus;

    /**
     * 异常状态：0-正常 1-异常
     */
    private List<Integer> abnormalStatusList;

    /**
     * 订单类型：0-充电订单 1-预约订单 2-占位订单
     */
    private Integer orderType;

    /**
     * 处理流程状态：完成（0-处理完毕）未完成（1-未返积分 2-未数据统计 3-未分佣）
     */
    private List<Integer> processSteps;

    /**
     * 是否有占桩费: 0-否 1-是
     */
    private Integer ownOccupy;

    /**
     * 结束原因编码
     */
    private Integer endReasonCode;

    private Integer siteId;

    public OrderPageDTO setTime() {
        if(startTime != null){
            startTime = DateUtil.beginOfDay(startTime);
        }

        if(endTime != null){
            endTime = DateUtil.endOfDay(endTime);
        }

        return this;
    }

}
