package com.things.cgomp.pay.vo.rule;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class SimpleRuleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 规则名称
     */
    private String name;
}
