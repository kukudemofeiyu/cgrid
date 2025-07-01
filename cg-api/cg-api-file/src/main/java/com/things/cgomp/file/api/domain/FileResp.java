package com.things.cgomp.file.api.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author things
 * @date 2025/3/6
 */
@Data
@Accessors(chain = true)
public class FileResp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件地址
     */
    private String url;
}
