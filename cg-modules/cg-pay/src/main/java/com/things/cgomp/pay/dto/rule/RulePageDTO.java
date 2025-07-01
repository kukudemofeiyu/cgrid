package com.things.cgomp.pay.dto.rule;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RulePageDTO extends PageDTO {

    /**
     * 运营商id
     */
    private Long operatorId;

    /**
     * 规则名称
     */
    private String name;

}
