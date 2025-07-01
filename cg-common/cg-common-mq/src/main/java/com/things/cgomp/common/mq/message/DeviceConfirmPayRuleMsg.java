package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceConfirmPayRuleMsg extends AbstractBody {

    private Long currentPayRuleId;

    private Integer currentPayModelId;

}
