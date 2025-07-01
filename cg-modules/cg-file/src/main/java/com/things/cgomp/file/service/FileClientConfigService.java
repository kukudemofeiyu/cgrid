package com.things.cgomp.file.service;

import com.things.cgomp.file.client.FileClientFactory;
import com.things.cgomp.file.config.FileConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 文件配置初始化服务
 * @author things
 * @date 2025/3/6
 */
@Slf4j
@Service
public class FileClientConfigService {

    @Resource
    private FileConfig fileConfig;
    @Resource
    private FileClientFactory fileClientFactory;

    @PostConstruct
    public void initClient(){
        String storage = fileConfig.getStorage();
        if (storage == null) {
            log.error("[initClient][未配置存储器，文件服务初始化失败]");
            return;
        }
        // 初始化文件客户端
        fileClientFactory.createOrUpdateFileClient(storage);
    }
}
