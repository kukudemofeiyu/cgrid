package com.things.cgomp.pay.vo.sitediscount;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.pay.api.enums.ActivityStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 站点折扣活动表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Getter
@Setter
@Accessors(chain = true)
public class SiteDiscountActivityVo implements Serializable {

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
     * 运营商名称
     */
    private String operatorName;

    /**
     * 0-未开始 1-进行中 2-已结束(自动到期) 3-已结束(手动停用)
     */
    private Integer status;

    /**
     * 活动开始时间
     */
    private LocalDate startTime;

    /**
     * 活动结束时间
     */
    private LocalDate endTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public SiteDiscountActivityVo formatStatus(){
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

        if(startTime.isAfter(LocalDate.now())){
            return ActivityStatusEnum.NOT_STARTED.getType();
        }

        if(!endTime.isBefore(LocalDate.now())){
            return ActivityStatusEnum.UNDER_WAY.getType();
        }

        return ActivityStatusEnum.ENDED_AUTO_EXPIRATION.getType();
    }
}
