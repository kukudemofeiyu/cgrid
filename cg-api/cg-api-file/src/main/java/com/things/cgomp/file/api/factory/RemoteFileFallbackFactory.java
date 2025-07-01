package com.things.cgomp.file.api.factory;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.file.api.RemoteFileService;
import com.things.cgomp.file.api.domain.FileResp;
import com.things.cgomp.file.api.domain.FileUploadReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 文件服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService> {

    @Override
    public RemoteFileService create(Throwable throwable) {
        log.error("文件服务调用失败:{}", throwable.getMessage());
        return new RemoteFileService() {

            @Override
            public R<FileResp> upload(FileUploadReq uploadReq) {
                return R.fail("上传文件失败:" + throwable.getMessage());
            }
        };
    }
}
