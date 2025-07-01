package com.things.cgomp.file.service;

import cn.hutool.core.util.StrUtil;
import com.things.cgomp.file.client.FileClient;
import com.things.cgomp.file.client.FileClientFactory;
import com.things.cgomp.file.domain.FilePreSignedUrlResp;
import com.things.cgomp.file.emuns.ErrorCodeConstants;
import com.things.cgomp.file.utils.FileTypeUtils;
import com.things.cgomp.file.utils.FileUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;


/**
 * 文件 Service 实现类
 *
 * @author things
 */
@Service
public class FileServiceImpl implements IFileService {

    @Resource
    private FileClientFactory fileClientFactory;

    @Override
    @SneakyThrows
    public String createFile(String name, String path, byte[] content) {
        // 计算默认的 path 名
        String type = FileTypeUtils.getMineType(content, name);
        path = FileUtils.generateFilePath(path, name);
        // 如果 name 为空，则使用 path 填充
        if (StrUtil.isEmpty(name)) {
            name = path;
        }
        // 上传到文件存储器
        FileClient client = fileClientFactory.getFileClient();
        if (client == null) {
            throw exception(ErrorCodeConstants.FILE_CLIENT_IS_NULL);
        }
        return client.upload(content, path, type);
    }

    @Override
    public void deleteFile(String path) throws Exception {
        // 从文件存储器中删除
        FileClient client = fileClientFactory.getFileClient();
        if (client == null) {
            throw exception(ErrorCodeConstants.FILE_CLIENT_IS_NULL);
        }
        client.delete(path);
    }

    @Override
    public byte[] getFileContent(Long configId, String path) throws Exception {
        FileClient client = fileClientFactory.getFileClient();
        if (client == null) {
            throw exception(ErrorCodeConstants.FILE_CLIENT_IS_NULL);
        }
        return client.getContent(path);
    }

    @Override
    public FilePreSignedUrlResp getFilePreSignedUrl(String path) throws Exception {
        FileClient fileClient = fileClientFactory.getFileClient();
        return fileClient.getPreSignedObjectUrl(path);
    }

    @Override
    public String getAbsoluteShareUrl(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        FileClient fileClient = fileClientFactory.getFileClient();
        return fileClient.getAbsoluteShareUrl(path);
    }
}
