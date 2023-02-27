package com.hcmute.project.instagram.backend.controller.admin;

import com.hcmute.project.instagram.backend.*;
import com.hcmute.project.instagram.backend.controller.admin.*;
import com.hcmute.project.instagram.backend.controller.common.*;
import com.hcmute.project.instagram.backend.controller.exception.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.enums.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.base.*;
import com.hcmute.project.instagram.backend.domain.exception.*;
import com.hcmute.project.instagram.backend.infrastructure.aws.minio.*;
import com.hcmute.project.instagram.backend.jwt.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

@CrossOrigin("*")
@RestController
//hoi lai, camelcase hay la a-a-a
@RequestMapping("api/admin/user")
public class AdminUserController {

  @Autowired
  private UserService userService;

  public AdminUserController() {

  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract searchUser() {
    ListUserResponse listUserResponse = this.userService.listUsers();
    return listUserResponse;
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract getUser(
    @PathVariable Integer id
  ) {
    GetUserResponse getUserResponse = this.userService.getUserById(id);
    return getUserResponse;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseBaseAbstract createUser(
    @RequestBody CreateUserRequest request
  ) {
    SuccessfulResponse createUserResponse = this.userService.createUser(request);
    return createUserResponse;
  }
  
  @PostMapping("{id}/update-avatar")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract updateUserAvatar(
    @PathVariable Integer id,
    @RequestParam("avatarFile") MultipartFile avatarFile
  ) throws IOException {
    UpdateUserAvatarRequest request = new UpdateUserAvatarRequest();

    request.setUserId(id);
    request.setAvatarBufferByteArray(avatarFile.getBytes());
    request.setUploadFileName(avatarFile.getOriginalFilename());

    SuccessfulResponse updateUserAvatarResponse = this.userService.updateAvatarById(request);
    return updateUserAvatarResponse;
  }
  
  @PutMapping("{id}/update")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract updateUser(
    @PathVariable Integer id,
    @RequestBody UpdateUserRequest request
  ) {
    request.setUserId(id);
    SuccessfulResponse updateUserResponse = this.userService.updateUser(request);
    return updateUserResponse;
  }

  @DeleteMapping("{id}/delete")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract deleteUser(
    @PathVariable Integer id
  ) {
    SuccessfulResponse updateUserResponse = this.userService.deleteUser(id);
    return updateUserResponse;
  }
}
