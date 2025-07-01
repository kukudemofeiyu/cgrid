package com.things.cgomp.file.client;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import com.things.cgomp.common.core.utils.SpringUtils;
import com.things.cgomp.file.emuns.FileStorageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 文件客户端的工厂实现类
 *
 * @author things
 */
@Slf4j
@Component
public class FileClientFactoryImpl implements FileClientFactory {

    /**
     * 文件客户端
     */
    private AbstractFileClient<?> client;

    @Override
    public FileClient getFileClient() {
        if (client == null) {
            log.error("[getFileClient][找不到客户端]");
        }
        return client;
    }

    @Override
    public <Config extends FileClientConfig> void createOrUpdateFileClient(String storage) {
        if (client == null) {
            client = this.createFileClient(storage);
            client.init();
        } else {
            //client.refresh(config);
        }
    }

    @SuppressWarnings("unchecked")
    private <Config extends FileClientConfig> AbstractFileClient<Config> createFileClient(String storage) {
        FileStorageEnum storageEnum = FileStorageEnum.getByStorage(storage);
        Assert.notNull(storageEnum, String.format("文件配置(%s) 为空", storageEnum));
        // 创建客户端
        return (AbstractFileClient<Config>) ReflectUtil.newInstance(storageEnum.getClientClass(), SpringUtils.getBean(storageEnum.getConfigClass()));
    }

}
