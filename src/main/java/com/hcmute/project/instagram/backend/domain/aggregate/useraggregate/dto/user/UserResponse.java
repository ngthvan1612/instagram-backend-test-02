package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user;

import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities.User;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.enums.UserRole;
import lombok.Data;

import java.util.Date;

/**
  * Data transfer object (DTO) for User
*/
@Data
public class UserResponse {
  
  private Integer id;
  
  private Date createdAt;
  
  private Date lastUpdatedAt;
  
  private Date deletedAt;
  
  private String username;
  
  private String password;
  
  private Integer age;
  
  private String address;
  
  private Date dateOfBirth;
  
  private String avatar;
  
  private UserRole role;
  

  public UserResponse(User user) {
    
    this.id = user.getId();
    this.createdAt = user.getCreatedAt();
    this.lastUpdatedAt = user.getLastUpdatedAt();
    this.deletedAt = user.getDeletedAt();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.age = user.getAge();
    this.address = user.getAddress();
    this.dateOfBirth = user.getDateOfBirth();
    this.avatar = user.getAvatar();
    this.role = user.getRole();
  }
}