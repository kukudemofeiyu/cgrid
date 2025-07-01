package com.things.cgomp.pay.vo;

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
public class RuleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 运营商id
     */
    private Long operatorId;

    /**
     * 运营商名称
     */
    private String operatorName;

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

    private String createByName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
