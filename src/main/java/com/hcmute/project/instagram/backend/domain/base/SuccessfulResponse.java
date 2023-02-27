package com.hcmute.project.instagram.backend.domain.base;

import org.springframework.http.HttpStatus;

public class SuccessfulResponse extends ResponseBaseAbstract {
  public SuccessfulResponse() {
    this.setStatusCode(HttpStatus.OK);
  }

  public SuccessfulResponse(HttpStatus statusCode) {
    this.setStatusCode(statusCode);
  }

  @Override
  public void setStatusCode(HttpStatus statusCode) {
    assert statusCode.value() <= 399;
    super.setStatusCode(statusCode);
  }
}