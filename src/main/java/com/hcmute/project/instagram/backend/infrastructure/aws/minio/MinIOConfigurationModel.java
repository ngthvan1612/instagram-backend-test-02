package com.hcmute.project.instagram.backend.infrastructure.aws.minio;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySources({
        @PropertySource("classpath:storage.production.properties"),
        @PropertySource("classpath:storage.development.properties")
})
@Data
public class MinIOConfigurationModel {

  @Value("${aws.minio.end-point}")
  private String endPoint;

  @Value("${aws.minio.access-key}")
  private String accessKey;

  @Value("${aws.minio.secret-key}")
  private String secretKey;

  @Value("${aws.minio.default-bucket}")
  private String defaultBucket;
}
