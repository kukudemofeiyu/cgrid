package com.things.cgomp.pay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 收费规则表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-03
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("pay_rule")
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模型编号（1-9999）
     */
    private Integer modelId;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 运营商id
     */
    private Long operatorId;

    /**
     * 是否为运营商默认规则:是 0:否
     */
    private Integer operatorDefault;

    /**
     * 是否为平台默认规则 1:是 0:否
     */
    private Integer sysDefault;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 0-正常 1-删除
     */
    @TableLogic
    private Integer delFlag;
}
