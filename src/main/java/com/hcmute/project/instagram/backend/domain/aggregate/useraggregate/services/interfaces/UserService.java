package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services.interfaces;

import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user.*;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public interface UserService {
  SuccessfulResponse createUser(CreateUserRequest request);
  GetUserResponse getUserById(Integer id);
  ListUserResponse listUsers();
  SuccessfulResponse updateUser(UpdateUserRequest request);
  SuccessfulResponse deleteUser(Integer id);

  SuccessfulResponse updateAvatarById(UpdateUserAvatarRequest request);

}
