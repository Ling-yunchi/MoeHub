package tv.moehub.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.model.BaseResult;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author wangrong
 * @date 2022/6/22 13:44
 */
@Service
@Slf4j
public class FileService {
    private final String bucket;
    private final MinioClient client;

    @SneakyThrows
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

        List<LifecycleRule> rules = new LinkedList<>();
        rules.add(new LifecycleRule(
                Status.ENABLED,
                new AbortIncompleteMultipartUpload(1),
                new Expiration((ResponseDate) null, 1, null),
                new RuleFilter("temp/*"),
                "TempUploads",
                null,
                null,
                null));
        this.client.setBucketLifecycle(
                SetBucketLifecycleArgs.builder().bucket(bucket).config(
                        new LifecycleConfiguration(rules)
                ).build());
    }


    public void upload(MultipartFile file, BaseResult<String> result) {
        if (file == null || file.isEmpty()) {
            result.construct(false, "文件为空");
            return;
        }
        try {
            String url = this.uploadFile(file, file.getOriginalFilename());
            result.construct(true, "上传成功", url);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.construct(false, "上传失败");
        }
    }

    public String uploadFile(MultipartFile file, String prefix) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        client.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(prefix)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build());
        return prefix;
    }

    public void moveFile(String oldPrefix, String newPrefix) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        client.copyObject(
                CopyObjectArgs.builder()
                        .bucket(bucket)
                        .object(newPrefix)
                        .source(
                                CopySource.builder()
                                        .bucket(bucket)
                                        .object(oldPrefix)
                                        .build())
                        .build());
        client.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(oldPrefix)
                        .build());
    }

    public void deleteFile(String prefix) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        client.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(prefix)
                        .build());
    }

    public String getFileUrl(String prefix) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return client.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucket)
                        .object(prefix)
                        .method(Method.GET)
                        .build());
    }
}
