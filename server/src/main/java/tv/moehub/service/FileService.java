package tv.moehub.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.model.BaseResult;

/**
 * @author wangrong
 * @date 2022/6/22 13:44
 */
@Service
@Slf4j
public class FileService {
    private final String bucket;
    private final MinioClient client;

    public FileService(MinioClient client, @Value("${minio.bucket}") String bucket) {
        this.bucket = bucket;
        this.client = client;
        try {
            boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public void upload(MultipartFile file, BaseResult<String> result) {
        if (file == null || file.isEmpty()) {
            result.construct(false, "文件为空");
            return;
        }
        try {
            String url = this.uploadFile(file, null, null);
            result.construct(true, "上传成功", url);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.construct(false, "上传失败");
        }
    }


    @SneakyThrows
    public String uploadFile(MultipartFile file, String filename, String path) {
        String prefix = (path == null ? "" : path) + "/" + (filename == null ? file.getOriginalFilename() : filename);
        client.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(file.getOriginalFilename())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build());
        String url = client.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucket)
                        .object(file.getOriginalFilename())
                        .method(Method.GET)
                        .build());
        return url.substring(0, url.indexOf("?"));
    }

    @SneakyThrows
    public void deleteFile(String fileName) {
        client.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .build());
    }
}
