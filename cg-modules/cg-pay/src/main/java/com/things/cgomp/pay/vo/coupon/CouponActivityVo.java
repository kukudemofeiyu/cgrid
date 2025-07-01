package com.things.cgomp.pay.vo.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.pay.api.enums.ActivityStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class CouponActivityVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
     * 运营商名称
     */
    private String operatorName;


    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     *  0-未开始 1-进行中 2-已结束(自动到期) 3-已结束(手动停用)
     */
    private Integer status;

    /**
     * 1-首次充电活动 2-单次充电活动 3-内部发券活动
     */
    private Integer type;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 修改人名称
     */
    private String updateByName;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    public CouponActivityVo formatStatus(){
        status  = buildStatus();
        return this;
    }

    private Integer buildStatus() {
        if(status == null
                || ActivityStatusEnum.ENDED_MANUAL_DISABLE.getType().equals(status)
                || startTime == null
        ){
            return status;
        }

        if(startTime.isAfter(LocalDateTime.now())){
            return ActivityStatusEnum.NOT_STARTED.getType();
        }

        if(!endTime.isBefore(LocalDateTime.now())){
            return ActivityStatusEnum.UNDER_WAY.getType();
        }

        return ActivityStatusEnum.ENDED_AUTO_EXPIRATION.getType();
    }

}
