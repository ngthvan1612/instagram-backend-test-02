package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public class GetUserResponse extends SuccessfulResponse {
  public GetUserResponse(UserResponse userResponse) {
    super();
    this.setData(userResponse);
  }
}
