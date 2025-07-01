package com.things.cgomp.file.controller;

import cn.hutool.core.io.IoUtil;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.utils.file.FileUtils;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.file.api.domain.FileResp;
import com.things.cgomp.file.api.domain.FileUploadReq;
import com.things.cgomp.file.domain.FilePreSignedUrlResp;
import com.things.cgomp.file.emuns.ErrorCodeConstants;
import com.things.cgomp.file.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 文件请求处理
 *
 * @author things
 */
@Slf4j
@Log(title = "文件服务")
@RestController
public class SysFileController {

    @Resource
    private IFileService fileService;

    /**
     * 文件上传请求
     */
    @Log(title = "文件上传", businessType = BusinessType.UPLOAD)
    @PostMapping("upload")
    public R<FileResp> upload(@Validated FileUploadReq uploadReq) {
        try {
            MultipartFile file = uploadReq.getFile();
            String path = uploadReq.getPath();
            // 上传并返回访问地址
            String url = fileService.createFile(file.getOriginalFilename(), path, IoUtil.readBytes(file.getInputStream()));
            FileResp resp = new FileResp()
                    .setName(FileUtils.getName(url))
                    .setUrl(url);
            return R.ok(resp);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw exception(ErrorCodeConstants.FILE_UPLOAD_FAIL);
        }
    }

    @GetMapping(value = "fileUrl", name = "获取文件完整路径")
    public R<FileResp> getFileUrl(String path) {
        // 获取文件完整路径
        String absoluteShareUrl = fileService.getAbsoluteShareUrl(path);
        FileResp resp = new FileResp()
                .setUrl(absoluteShareUrl)
                .setName(path);
        return R.ok(resp);
    }

    @Log(title = "生成签名url", businessType = BusinessType.OTHER)
    @PostMapping(value = "generateUploadUrl", name = "生成签名url")
    public R<FileResp> getUploadUrl(@RequestParam String fileName) {
        try {
            // 上传并返回访问地址
            FilePreSignedUrlResp preSignedUrlResp = fileService.getFilePreSignedUrl(fileName);
            FileResp resp = new FileResp()
                    .setUrl(preSignedUrlResp.getUploadUrl())
                    .setName(fileName);
            return R.ok(resp);
        } catch (Exception e) {
            log.error("生成签名url失败", e);
            throw exception(ErrorCodeConstants.FILE_URL_PRE_SIGN_FAIL);
        }
    }
}