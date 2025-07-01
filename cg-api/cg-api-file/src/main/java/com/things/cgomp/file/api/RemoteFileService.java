package com.things.cgomp.file.api;

import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.file.api.domain.FileResp;
import com.things.cgomp.file.api.domain.FileUploadReq;
import com.things.cgomp.file.api.factory.RemoteFileFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 文件服务
 *
 * @author things
 */
@FeignClient(contextId = "remoteFileService",
        value = ServiceNameConstants.FILE_SERVICE,
        //url = "http://192.168.3.45:9300",
        fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService {

    /**
     * 上传文件
     *
     * @param uploadReq 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<FileResp> upload(FileUploadReq uploadReq);
}
