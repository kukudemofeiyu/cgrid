package com.things.cgomp.file.client;


import com.things.cgomp.file.emuns.FileStorageEnum;

public interface FileClientFactory {

    /**
     * 获得文件客户端
     *
     * @return 文件客户端
     */
    FileClient getFileClient();

    /**
     * 创建文件客户端
     *
     * @param storage 存储器的枚举 {@link FileStorageEnum}
     */
    <Config extends FileClientConfig> void createOrUpdateFileClient(String storage);

}
