package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.enums.UserRole;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserRequest {
  @JsonIgnore
  private Integer userId;
  
  private String username;
  private String password;
  private Integer age;
  private String address;
  private Date dateOfBirth;
  private String avatar;
  private UserRole role;
  
}
