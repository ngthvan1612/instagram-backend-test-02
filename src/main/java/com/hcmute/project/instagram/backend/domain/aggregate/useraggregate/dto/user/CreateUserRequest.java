package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user;

import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.enums.UserRole;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public class CreateUserRequest {
  
  @NotEmpty(message = "tên người dùng không được trống")
  private String username;
  
  @NotEmpty(message = "mật khẩu không được trống")
  private String password;
  
  
  private Integer age;
  
  @NotEmpty(message = "địa chỉ không được trống")
  private String address;
  
  
  private Date dateOfBirth;
  
  
  private String avatar;
  
  @NotEmpty(message = "vai trò không được trống")
  private UserRole role;
  
  
}