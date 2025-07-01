package com.things.cgomp.pay.dto.rule;

import com.things.cgomp.device.api.dto.RuleFeeDTO;
import com.things.cgomp.device.api.dto.RuleTimeDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class RuleContentDTO implements Serializable {

    private List<RuleFeeDTO> fees;

    private List<RuleTimeDTO> times;

    public RuleContentDTO format(){
        if(CollectionUtils.isEmpty(fees)){
            return this;
        }

        fees.forEach(RuleFeeDTO::format);
        return this;
    }
}
