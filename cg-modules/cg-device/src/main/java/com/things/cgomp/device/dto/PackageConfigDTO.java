package com.things.cgomp.device.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PackageConfigDTO {

    private String parent;

    private String entity;

    private String xml;

    private PathInfoDTO pathInfo;

}
