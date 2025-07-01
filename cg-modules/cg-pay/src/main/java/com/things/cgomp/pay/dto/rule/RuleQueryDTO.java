package com.things.cgomp.pay.dto.rule;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RuleQueryDTO extends BaseEntity {

    /**
     * 充电桩ID
     */
    private Long deviceId;
}
