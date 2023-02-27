package com.hcmute.project.instagram.backend.domain.exception;

import org.springframework.http.HttpStatus;

public class ServiceExceptionFactory {
    public ServiceExceptionFactory() {

    }

    /**
     * Lỗi xảy ra khi không tìm thấy tài nguyên yêu cầu
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase notFound() {
      return new ServiceExceptionBase(HttpStatus.NOT_FOUND);
    }

    /**
     * Lỗi xảy ra khi trùng dữ liệu
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase duplicate() {
      return new ServiceExceptionBase(HttpStatus.CONFLICT);
    }

    /**
     * Lỗi không xác định (lỗi thuộc về phía user)
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase badRequest() {
      return new ServiceExceptionBase(HttpStatus.BAD_REQUEST);
    }

    /**
     * Chưa đăng nhập
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase unauthorized() {
      return new ServiceExceptionBase(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Đã đăng nhập nhưng KHÔNG đủ quyền truy cập vào tài nguyên
     * @return ServiceExceptionBase
     */
    public static ServiceExceptionBase forbidden() {
      return new ServiceExceptionBase(HttpStatus.FORBIDDEN);
    }
}