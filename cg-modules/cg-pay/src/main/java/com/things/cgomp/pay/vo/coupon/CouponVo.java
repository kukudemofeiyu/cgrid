package com.things.cgomp.pay.vo.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.pay.api.enums.CouponStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-26
 */
@Getter
@Setter
@Accessors(chain = true)
public class CouponVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 编号
     */
    private String sn;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 1-首次充电活动 2-单次充电活动 3-内部发券活动
     */
    private Integer activityType;

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 运营商名称
     */
    private String operatorName;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userTime;

    /**
     * 0-未使用 1-已使用 2-失效
     */
    private Integer status;

    public CouponVo formatStatus(){
        status = buildStatus();
        return this;
    }

    private Integer buildStatus() {
        if(status == null
                || CouponStatusEnum.USED.getType().equals(status)
                || startTime == null
        ){
            return status;
        }

        if(endTime.isBefore(LocalDateTime.now())){
            return CouponStatusEnum.EXPIRATION.getType();
        }

         return CouponStatusEnum.UNUSED.getType();
    }

}
