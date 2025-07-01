package com.things.cgomp.device.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StrategyConfigDTO {

    private String tableName;

    private String tablePrefix;

}
