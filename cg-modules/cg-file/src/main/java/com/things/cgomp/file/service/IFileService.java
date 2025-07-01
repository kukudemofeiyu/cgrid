package com.things.cgomp.file.service;

import com.things.cgomp.file.domain.FilePreSignedUrlResp;

/**
 * 文件 Service 接口
 *
 * @author things
 */
public interface IFileService {

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param name    文件名称
     * @param path    文件路径
     * @param content 文件内容
     * @return 文件路径
     */
    String createFile(String name, String path, byte[] content);
    /**
     * 删除文件
     *
     * @param path 文件路径
     */
    void deleteFile(String path) throws Exception;

    /**
     * 获得文件内容
     *
     * @param configId 配置编号
     * @param path     文件路径
     * @return 文件内容
     */
    byte[] getFileContent(Long configId, String path) throws Exception;

    /**
     * 生成文件预签名地址信息
     *
     * @param path 文件路径
     * @return 预签名地址信息
     */
    FilePreSignedUrlResp getFilePreSignedUrl(String path) throws Exception;

    /**
     * 获取完整的请求地址
     * @param path 路径
     * @return String
     */
    String getAbsoluteShareUrl(String path);
}
