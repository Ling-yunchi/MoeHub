package tv.moehub.config;

import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangrong
 * @date 2022/6/22 13:54
 */
@Configuration
public class MinioConfig {
    @Value("${minio.endpoint}")
    String endpoint;
    @Value("${minio.access-key}")
    String accessKey;
    @Value("${minio.secret-key}")
    String secretKey;

    @Bean
    public MinioClient minioClient() {
        try {
            return MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
