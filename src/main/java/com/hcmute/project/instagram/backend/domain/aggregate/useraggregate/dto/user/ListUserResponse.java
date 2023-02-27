package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

import java.util.List;

public class ListUserResponse extends SuccessfulResponse {
  public ListUserResponse(List<UserResponse> userResponses) {
    super();
    this.setData(userResponses);
  }
}
