package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user;

import lombok.Data;

@Data
public class UpdateUserAvatarRequest {
  private Integer userId;
  private byte[] avatarBufferByteArray;
  private String uploadFileName;

  public UpdateUserAvatarRequest() {
    
  }
}
