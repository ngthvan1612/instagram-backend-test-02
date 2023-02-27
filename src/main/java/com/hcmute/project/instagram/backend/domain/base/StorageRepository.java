package com.hcmute.project.instagram.backend.domain.base;

import java.io.InputStream;

public interface StorageRepository {
    String saveUploadedStream(String uploadFileName, InputStream inputStream, long length);
}