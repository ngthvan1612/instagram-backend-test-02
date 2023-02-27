package com.hcmute.project.instagram.backend.infrastructure.aws.minio;

import com.hcmute.project.instagram.backend.*;
import com.hcmute.project.instagram.backend.controller.admin.*;
import com.hcmute.project.instagram.backend.controller.common.*;
import com.hcmute.project.instagram.backend.controller.exception.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.enums.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.base.*;
import com.hcmute.project.instagram.backend.domain.exception.*;
import com.hcmute.project.instagram.backend.infrastructure.aws.minio.*;
import com.hcmute.project.instagram.backend.jwt.*;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.errors.*;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.File;
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
