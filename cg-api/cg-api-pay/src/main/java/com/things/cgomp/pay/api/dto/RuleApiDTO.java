package com.things.cgomp.pay.api.dto;

import com.things.cgomp.device.api.dto.RuleFeeDTO;
import com.things.cgomp.device.api.dto.RuleTimeDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
public class RuleApiDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 规则名称
     */
    private String name;

    private List<RuleFeeDTO> fees;

    private List<RuleTimeDTO> times;

}
