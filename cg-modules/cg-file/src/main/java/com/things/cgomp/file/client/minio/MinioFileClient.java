package com.things.cgomp.file.client.minio;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.things.cgomp.file.client.AbstractFileClient;
import com.things.cgomp.file.domain.FilePreSignedUrlResp;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;

/**
 * 基于 S3 协议的文件客户端，实现 MinIO等服务
 *
 * @author things
 */
@Slf4j
public class MinioFileClient extends AbstractFileClient<MinioClientConfig> {

    private MinioClient client;

    public MinioFileClient(MinioClientConfig config) {
        super(config);
    }

    @Override
    protected void doInit() {
        // 补全 domain
        if (StrUtil.isEmpty(config.getDomain())) {
            config.setDomain(buildDomain());
        }
        // 初始化客户端
        client = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getAccessSecret())
                .build();
        // 初始化bucket
        initBucket();
        log.info("[MinioFileClient][初始化完成]");
    }

    /**
     * 初始化 bucket， 不存在则创建
     */
    private void initBucket() {
        try {
            BucketExistsArgs bucketExistsArgs = buildBucketExistsArgs();
            boolean bucketExists = client.bucketExists(bucketExistsArgs);
            if (!bucketExists) {
                MakeBucketArgs makeBucketArgs = buildMakeBucketArgs();
                client.makeBucket(makeBucketArgs);
                log.info("[initBucket][创建bucket({}) 完成]", config.getBucket());
            }
        } catch (Exception e) {
            log.error("[initBucket][初始化bucket({}) 失败]", config.getBucket(), e);
        }
    }

    @Override
    public String upload(byte[] content, String path, String type) throws Exception {
        PutObjectArgs putObjectArgs = buildPutObjectArgs(content, path, type);
        // 执行上传
        client.putObject(putObjectArgs);
        // 拼接返回路径
        //return config.getDomain() + "/" + path;
        return buildReturnUrl(path);
    }

    private String buildReturnUrl(String path){
        return StrUtil.format("{}/{}", config.getBucket(), path);
    }

    @Override
    public void delete(String path) throws Exception {
        RemoveObjectArgs removeObjectArgs = buildRemoveObjectArgs(path);
        client.removeObject(removeObjectArgs);
    }

    @Override
    public byte[] getContent(String path) throws Exception {
        GetObjectArgs getObjectArgs = buildGetObjectArgs(path);
        GetObjectResponse objResp = client.getObject(getObjectArgs);
        return IoUtil.readBytes(objResp);
    }

    @Override
    public FilePreSignedUrlResp getPreSignedObjectUrl(String path) throws Exception {
        GetPresignedObjectUrlArgs preSignedObjectUrlArgs = buildGetPreSignedObjectUrlArgs(path);
        // 生成上传 URL
        String uploadUrl = client.getPresignedObjectUrl(preSignedObjectUrlArgs);
        return new FilePreSignedUrlResp(uploadUrl, config.getDomain() + "/" + path);
    }

    private BucketExistsArgs buildBucketExistsArgs() {
        return BucketExistsArgs.builder()
                .bucket(config.getBucket())
                .build();
    }

    @Override
    public String getAbsoluteShareUrl(String path) {
        return config.getDomain() + "/" + path;
    }

    private MakeBucketArgs buildMakeBucketArgs() {
        return MakeBucketArgs.builder()
                .bucket(config.getBucket())
                .build();
    }

    /**
     * 基于 bucket + endpoint 构建访问的 Domain 地址
     *
     * @return Domain 地址
     */
    private String buildDomain() {
        if (HttpUtil.isHttp(config.getEndpoint()) || HttpUtil.isHttps(config.getEndpoint())) {
            return StrUtil.format("{}/{}", config.getEndpoint(), config.getBucket());
        }
        return StrUtil.format("https://{}.{}", config.getBucket(), config.getEndpoint());
    }

    private RemoveObjectArgs buildRemoveObjectArgs(String path) {
        return RemoveObjectArgs.builder()
                .bucket(config.getBucket())
                .object(path)
                .build();
    }

    private PutObjectArgs buildPutObjectArgs(byte[] content, String path, String type) {
        return PutObjectArgs.builder()
                .bucket(config.getBucket())
                .object(path)
                .stream(new ByteArrayInputStream(content), content.length, -1)
                .contentType(type)
                .build();
    }

    private GetPresignedObjectUrlArgs buildGetPreSignedObjectUrlArgs(String path) {
        return GetPresignedObjectUrlArgs.builder()
                .expiry(config.getPreSignedDuration(), TimeUnit.SECONDS)
                .method(Method.PUT)
                .method(Method.POST)
                .bucket(config.getBucket())
                .object(path).build();
    }

    private GetObjectArgs buildGetObjectArgs(String path) {
        return GetObjectArgs.builder()
                .bucket(config.getBucket())
                .object(path)
                .build();
    }
}
