package com.things.cgomp.file.client;


import com.things.cgomp.file.domain.FilePreSignedUrlResp;
import com.things.cgomp.file.emuns.ErrorCodeConstants;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 文件客户端
 *
 * @author things
 */
public interface FileClient {

    /**
     * 上传文件
     *
     * @param content 文件流
     * @param path    相对路径
     * @return 完整路径，即 HTTP 访问地址
     * @throws Exception 上传文件时，抛出 Exception 异常
     */
    String upload(byte[] content, String path, String type) throws Exception;

    /**
     * 删除文件
     *
     * @param path 相对路径
     * @throws Exception 删除文件时，抛出 Exception 异常
     */
    void delete(String path) throws Exception;

    /**
     * 获得文件的内容
     *
     * @param path 相对路径
     * @return 文件的内容
     */
    byte[] getContent(String path) throws Exception;

    /**
     * 获得文件预签名地址
     *
     * @param path 相对路径
     * @return 文件预签名地址
     */
    default FilePreSignedUrlResp getPreSignedObjectUrl(String path) throws Exception {
        throw exception(ErrorCodeConstants.NOT_SUPPORT_OPERATION);
    }

    /**
     * 获取完整的请求地址
     * @param path 路径
     * @return String
     */
    String getAbsoluteShareUrl(String path);
}
