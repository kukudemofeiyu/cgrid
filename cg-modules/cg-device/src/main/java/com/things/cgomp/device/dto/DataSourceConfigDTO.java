package com.things.cgomp.device.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Data
@Accessors(chain = true)
public class DataSourceConfigDTO {

    private String hostname;

    private Integer port;

    private String dbName;

    private String username;

    private String password;

    @NotNull
    public String buildUrl() {
        return "jdbc:mysql://"
                + hostname
                + ":"
                + port
                + "/"
                + dbName
                + "?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    }
}
