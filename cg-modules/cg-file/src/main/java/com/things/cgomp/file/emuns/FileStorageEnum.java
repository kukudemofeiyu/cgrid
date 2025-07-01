package com.things.cgomp.file.emuns;

import cn.hutool.core.util.ArrayUtil;
import com.things.cgomp.file.client.FileClient;
import com.things.cgomp.file.client.FileClientConfig;
import com.things.cgomp.file.client.minio.MinioClientConfig;
import com.things.cgomp.file.client.minio.MinioFileClient;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件存储器枚举
 *
 * @author things
 */
@AllArgsConstructor
@Getter
public enum FileStorageEnum {

    MINIO("minio", MinioClientConfig.class, MinioFileClient.class),
    ;

    /**
     * 存储器
     */
    private final String storage;
    /**
     * 配置类
     */
    private final Class<? extends FileClientConfig> configClass;
    /**
     * 客户端类
     */
    private final Class<? extends FileClient> clientClass;

    public static FileStorageEnum getByStorage(String storage) {
        return ArrayUtil.firstMatch(o -> o.getStorage().equals(storage), values());
    }

    public static FileStorageEnum getDefaultClient(){
        return MINIO;
    }
}
