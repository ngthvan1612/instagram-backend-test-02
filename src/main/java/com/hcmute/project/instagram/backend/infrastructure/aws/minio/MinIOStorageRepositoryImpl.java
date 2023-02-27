package com.hcmute.project.instagram.backend.infrastructure.aws.minio;

import com.hcmute.project.instagram.backend.domain.base.StorageRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Repository
public class MinIOStorageRepositoryImpl implements StorageRepository {
    private final MinIOConfigurationModel minIOConfigurationModel;

    private final MinioClient client;

    @Autowired
    public MinIOStorageRepositoryImpl(MinIOConfigurationModel minIOConfigurationModel) {
        this.minIOConfigurationModel = minIOConfigurationModel;

        this.client = MinioClient.builder()
                .endpoint(this.minIOConfigurationModel.getEndPoint())
                .credentials(this.minIOConfigurationModel.getAccessKey(), this.minIOConfigurationModel.getSecretKey())
                .build();
    }

    public String fixedFileNameWithUUID(String uploadFileName) {
        int pos = uploadFileName.lastIndexOf(".");
        if (0 < pos && pos < uploadFileName.length()) {
            return uploadFileName.substring(0, pos) + UUID.randomUUID() + uploadFileName.substring(pos);
        }
        else {
            return uploadFileName + UUID.randomUUID() + ".jpg";
        }
    }

    @Override
    public String saveUploadedStream(String uploadFileName, InputStream inputStream, long length) {
        String imagePath = "image/";
        String imageFullPath = imagePath + this.fixedFileNameWithUUID(uploadFileName);

        PutObjectArgs uploadImageArgs = PutObjectArgs.builder()
                .bucket(this.minIOConfigurationModel.getDefaultBucket())
                .object(imageFullPath)
                .contentType("image/jpeg")
                .stream(inputStream, length, 5L * 1024 * 1024)
                .build();

        try {
            this.client.putObject(uploadImageArgs);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            e.printStackTrace();
            return null;
        }

        return imageFullPath;
    }
}
