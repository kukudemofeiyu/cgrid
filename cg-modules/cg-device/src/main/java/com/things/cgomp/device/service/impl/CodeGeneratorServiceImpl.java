package com.things.cgomp.device.service.impl;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.things.cgomp.device.dto.*;
import com.things.cgomp.device.service.CodeGeneratorService;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    @Override
    public void generate(CodeGeneratorDTO generatorDTO) {
        DataSourceConfig.Builder dataSourceConfigBuilder = getDataSourceConfigBuilder(generatorDTO.getDataSourceConfig());

        FastAutoGenerator.create(
                        dataSourceConfigBuilder
                )
                .globalConfig(builder -> globalConfig(generatorDTO.getGlobalConfig(), builder))
                .packageConfig(builder -> packageConfig(
                        generatorDTO.getPackageConfig(),
                        builder
                ))
                .strategyConfig(builder -> strategyConfig(
                        generatorDTO.getStrategyConfig(),
                        builder
                ))
                .execute();

    }

    private void strategyConfig(
            StrategyConfigDTO strategyConfig,
            StrategyConfig.Builder builder
    ) {
        builder.entityBuilder()
                .enableLombok()
                .enableChainModel();
        builder.controllerBuilder()
                        .enableRestStyle();
        // 设置需要生成的表名--需要的时候直接替换表名即可
        builder.addInclude(strategyConfig.getTableName())
                .addTablePrefix(strategyConfig.getTablePrefix()); // 设置过滤表前缀
    }

    private void globalConfig(
            GlobalConfigDTO globalConfig,
            GlobalConfig.Builder builder
    ) {
        String outputDir = getOutputDir(globalConfig.getRelativeDir());
        builder.outputDir(outputDir);
    }

    private void packageConfig(
            PackageConfigDTO packageConfigDTO,
            PackageConfig.Builder builder) {
        builder.parent(packageConfigDTO.getParent());

        String entity = packageConfigDTO.getEntity();
        if(StringUtils.isNotBlank(entity)){
            builder.entity(entity);
        }

        String xml = packageConfigDTO.getXml();
        if(StringUtils.isNotBlank(xml)){
            builder.xml(xml);
        }

        Map<OutputFile, String> pathInfo = getPathInfoMap(packageConfigDTO.getPathInfo());
        builder.pathInfo(pathInfo);
    }

    @NotNull
    private Map<OutputFile, String> getPathInfoMap(PathInfoDTO pathInfo) {
        if(pathInfo == null){
            return new HashMap<>();
        }

        Map<OutputFile, String> pathInfoMap  = new HashMap<>();

        String xml = pathInfo.getXml();
        if(StringUtils.isNotBlank(xml)){
            String s = System.getProperty("user.dir")
                    + xml;
            pathInfoMap.put(OutputFile.xml,s);
        }
        return pathInfoMap;
    }

    @NotNull
    private com.baomidou.mybatisplus.generator.config.DataSourceConfig.Builder getDataSourceConfigBuilder(
            DataSourceConfigDTO dataSourceConfig
    ) {
        return new com.baomidou.mybatisplus.generator.config.DataSourceConfig.Builder(
                dataSourceConfig.buildUrl(),
                dataSourceConfig.getUsername(),
                dataSourceConfig.getPassword()
        );
    }

    @NotNull
    private String getOutputDir(String relativeDir) {
        return System.getProperty("user.dir")
                + relativeDir
                + "/src/main/java";
    }

}
