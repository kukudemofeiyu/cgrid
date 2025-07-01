package com.things.cgomp.device.dto;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class CodeGeneratorDTO {

    private DataSourceConfigDTO dataSourceConfig;

    private GlobalConfigDTO globalConfig;

    private PackageConfigDTO packageConfig;

    private StrategyConfigDTO strategyConfig;
}
