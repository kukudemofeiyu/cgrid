package com.things.cgomp.device.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateChargeGridRulesDTO {

    /**
     * 旧计费规则id
     */
    private Long oldPayRuleId;

    /**
     * 新计费规则id
     */
    private Long newPayRuleId;

    /**
     * 新计费模型编号
     */
    private Integer newPayModelId;

    private RuleDTO ruleDTO;


}
