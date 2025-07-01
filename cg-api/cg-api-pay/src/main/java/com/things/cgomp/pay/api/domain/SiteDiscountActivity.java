package com.things.cgomp.pay.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
@TableName("pay_site_discount_activity")
public class SiteDiscountActivity implements Serializable {

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
     * 0-未开始 1-进行中 2-已结束(自动到期) 3-已结束(手动停用)
     */
    private Integer status;

    /**
     * 可用站点维度(0-全部站点 1-部分站点)
     */
    private Integer siteDimension;

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

    /**
     * 0-正常 1-删除
     */
    private Integer delFlag;

    /**
     * 模板id列表
     */
    @TableField(exist = false)
    @NotEmpty(message = "模板id列表不能为空")
    private List<Long> templateIds;

    /**
     * 模板id
     */
    @TableField(exist = false)
    private Long templateId;

    /**
     * 站点id列表
     */
    @TableField(exist = false)
    private List<Long> siteIds;

    /**
     * 模板列表
     */
    @TableField(exist = false)
    private List<SiteDiscountTemplate> templates;

}
