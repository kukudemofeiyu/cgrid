package com.things.cgomp.file.api.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 文件上传请求对象
 * @author things
 * @date 2025/3/6
 */
@Data
@Accessors(chain = true)
public class FileUploadReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "文件不能为空")
    private MultipartFile file;
    /**
     * 文件自定义路径
     */
    private String path;
}
