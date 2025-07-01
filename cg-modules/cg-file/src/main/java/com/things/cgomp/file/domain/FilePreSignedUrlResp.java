package com.things.cgomp.file.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件预签名地址 Response
 *
 * @author things
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePreSignedUrlResp {

    /**
     * 文件上传 URL（用于上传）
     */
    private String uploadUrl;

    /**
     * 文件 URL（用于读取、下载等）
     */
    private String url;

}
