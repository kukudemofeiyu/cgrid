package com.things.cgomp.pay.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.dto.coupon.ActivityCouponDTO;
import com.things.cgomp.pay.dto.coupon.CouponActivityConfigDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券活动表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_coupon_activity")
public class CouponActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     *  0-未开始 1-进行中 2-已结束(自动到期) 3-已结束(手动停用)
     */
    private Integer status;

    /**
     * 1-首次充电活动 2-单次充电活动 3-内部发券活动
     */
    private Integer type;

    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    /**
     * 规则url
     */
    private String rule;

    /**
     * 介绍
     */
    private String remark;

    /**
     * 活动配置
     */
    private String config;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private CouponActivityConfigDTO activityConfig;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> userIds;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String templateIdsStr;

    public CouponActivity setConfig(){
        config = buildConfig();
        return this;
    }

    public CouponActivity setActivityConfig(){
        activityConfig = buildActivityConfig();
        return this;
    }

    public CouponActivity filterCoupons(){
        if (StringUtils.isBlank(templateIdsStr)
                || activityConfig == null) {
            return this;
        }

        List<Long> templateIds = Arrays.stream(templateIdsStr.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        activityConfig.filterCoupons(templateIds);
        return this;
    }

    private CouponActivityConfigDTO buildActivityConfig() {
        if (StringUtils.isBlank(config)) {
            return null;
        }

        return JSON.parseObject(config, CouponActivityConfigDTO.class);
    }

    private String buildConfig(){
        CouponActivityConfigDTO activityConfig = this.activityConfig;
        if(activityConfig == null){
            activityConfig = new CouponActivityConfigDTO();
        }

        return JSON.toJSONString(activityConfig);
    }

    public List<ActivityCouponDTO> buildCoupons() {
        if(activityConfig == null){
            return new ArrayList<>();
        }

        return activityConfig.getCoupons();
    }

    public List<Long> buildTemplateIds() {
        if (activityConfig == null) {
            return new ArrayList<>();
        }

        return activityConfig.buildTemplateIds();
    }

    public boolean meetCondition(
            OrderInfo orderInfo
    ) {
        if (activityConfig == null) {
            return false;
        }

        return activityConfig.meetCondition(orderInfo);
    }

    public Integer buildReceiveLimit() {
        if(activityConfig == null){
            return null;
        }
        return activityConfig.getReceiveLimit();
    }

}
