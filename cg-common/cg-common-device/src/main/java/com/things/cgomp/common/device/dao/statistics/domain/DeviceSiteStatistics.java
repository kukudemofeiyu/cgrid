package com.things.cgomp.common.device.dao.statistics.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSiteStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 统计日期
     * yyyy-MM-dd
     */
    private String statisticsDate;
    /**
     * 充电次数
     */
    private Integer chargeCount;
    /**
     * 充电时长（小时）
     */
    private BigDecimal chargeTime;
    /**
     * 充电量
     */
    private BigDecimal chargeElectricity;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 充电金额
     */
    private BigDecimal chargeAmount;
    /**
     * 服务金额
     */
    private BigDecimal serviceAmount;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
