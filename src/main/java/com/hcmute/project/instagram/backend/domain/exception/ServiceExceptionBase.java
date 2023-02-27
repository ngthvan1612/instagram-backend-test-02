package com.hcmute.project.instagram.backend.domain.exception;

import org.springframework.http.HttpStatus;

public class ServiceExceptionBase extends RuntimeException {
  private ServiceExceptionResponse serviceExceptionResponse;

  public ServiceExceptionBase(HttpStatus httpStatus ) {
    this.serviceExceptionResponse = new ServiceExceptionResponse();
    this.serviceExceptionResponse.setStatusCode(httpStatus);
  }

  public ServiceExceptionBase addMessage(String message) {
    this.serviceExceptionResponse.addMessage(message);
    return this;
  }

  public ServiceExceptionBase withData(Object data) {
    this.serviceExceptionResponse.setData(data);
    return this;
  }

  public ServiceExceptionResponse getServiceExceptionResponse() {
    return serviceExceptionResponse;
  }

  public void setServiceExceptionResponse(ServiceExceptionResponse serviceExceptionResponse) {
    this.serviceExceptionResponse = serviceExceptionResponse;
  }
}
