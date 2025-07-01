package com.things.cgomp.order.api.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.things.cgomp.common.core.enums.CommissionLevel;
import com.things.cgomp.common.core.enums.CommissionType;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 分成规则表 order_commission_rule
 *
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@TableName("order_commission_rule")
public class OrderCommissionRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long Id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 分成比例
     */
    private BigDecimal ratio;
    /**
     * 分成等级
     * @see CommissionLevel
     */
    private Integer level;
    /**
     * 分成类型
     * @see CommissionType
     */
    private Integer type;
    /**
     * 状态
     * 0生效 1停用
     */
    private Integer status;
    /**
     * 删除标识
     * 1删除 0未删除
     */
    private Integer delFlag;
    /**
     * 剩余比例
     */
    @TableField(exist = false)
    private BigDecimal leftRatio;
    /**
     * 分成者名称
     */
    @TableField(exist = false)
    private String realName;
    /**
     * 运营商名称
     */
    @TableField(exist = false)
    private String operatorName;
    /**
     * 运营商手机号码
     */
    @TableField(exist = false)
    private String operatorMobile;
    /**
     * 站点名称
     */
    @TableField(exist = false)
    private String siteName;
}
