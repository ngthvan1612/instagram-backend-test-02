package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services;

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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {
  
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private StorageRepository storageRepository;

  public UserServiceImpl() {

  }

  //TODO: Validate with annotation
  //TODO: check fk before create & update
  //TODO: update unique column for delete
  //TODO: swagger
  //TODO: authorize
  //TODO: hash password
  //TODO: loggggggggg

  @Override
  public SuccessfulResponse createUser(CreateUserRequest request) {
    //Validate
    if (this.userRepository.existsByUsername(request.getUsername())) {
      throw ServiceExceptionFactory.duplicate()
        .addMessage("???? t???n t???i ng?????i d??ng v???i username l?? " + request.getUsername());
    }

    //Check null
    
    User user = new User();
    
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    user.setAge(request.getAge());
    user.setAddress(request.getAddress());
    user.setDateOfBirth(request.getDateOfBirth());
    user.setAvatar(request.getAvatar());
    user.setRole(request.getRole());

    //Save to database
    this.userRepository.save(user);

    //Return
    UserResponse userDTO = new UserResponse(user);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(userDTO);
    response.addMessage("T???o ng?????i d??ng th??nh c??ng");

    return response;
  }

  @Override
  public GetUserResponse getUserById(Integer id) {
    if (!this.userRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Kh??ng t??m th???y ng?????i d??ng n??o v???i id l?? " + id);
    }

    User user = this.userRepository.findById(id).get();
    UserResponse userDTO = new UserResponse(user);
    GetUserResponse response = new GetUserResponse(userDTO);

    response.addMessage("L???y d??? li???u th??nh c??ng");

    return response;
  }

  @Override
  public ListUserResponse searchUsers(Map<String, String> queries) {
    List<UserResponse> listUserResponses = this.userRepository.searchUser(queries)
          .stream().map(user -> new UserResponse(user)).toList();
    
    ListUserResponse response = new ListUserResponse(listUserResponses);
    response.addMessage("L???y d??? li???u th??nh c??ng");

    return response;
  }

  @Override
  public SuccessfulResponse updateUser(UpdateUserRequest request) {
    //Check record exists
    if (!this.userRepository.existsById(request.getUserId())) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Kh??ng t??m th???y ng?????i d??ng n??o v???i id l?? " + request.getUserId());
    }

    //Read data from request
    User user = this.userRepository.findById(request.getUserId()).get();
    
    
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    user.setAge(request.getAge());
    user.setAddress(request.getAddress());
    user.setDateOfBirth(request.getDateOfBirth());
    user.setAvatar(request.getAvatar());
    user.setRole(request.getRole());

    //Validate unique
    
    if (this.userRepository.existsByUsernameExceptId(request.getUsername(), request.getUserId())) {
      throw ServiceExceptionFactory.duplicate()
        .addMessage("???? t???n t???i ng?????i d??ng v???i t??n ng?????i d??ng l?? " + request.getUsername());
    }
    

    //Update last changed time
    user.setLastUpdatedAt(new Date());

    //Store
    this.userRepository.save(user);

    //Return
    UserResponse userDTO = new UserResponse(user);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(userDTO);
    response.addMessage("C???p nh???t ng?????i d??ng th??nh c??ng");

    return response;
  }
  
  @Override
  public SuccessfulResponse updateAvatarById(UpdateUserAvatarRequest request) {
    if (!this.userRepository.existsById(request.getUserId())) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Kh??ng t??m th???y ng?????i d??ng n??o c?? id = " + request.getUserId());
    }

    //Save to MinIO
    InputStream preparedStream = new ByteArrayInputStream(request.getAvatarBufferByteArray());
    String newMinIOUrl = this.storageRepository.saveUploadedStream(
      request.getUploadFileName(),
      preparedStream,
      request.getAvatarBufferByteArray().length
    );

    if (newMinIOUrl == null) {
      throw ServiceExceptionFactory.badRequest()
        .addMessage("T???i l??n l???i");
    }

    //Save to database
    User user = this.userRepository.findById(request.getUserId()).get();
    user.setAvatar(newMinIOUrl);
    
    this.userRepository.save(user);

    SuccessfulResponse successResponse = new SuccessfulResponse(HttpStatus.OK);
    successResponse.addMessage("C???p nh???t ???nh ?????i di???n th??nh c??ng");
    
    return successResponse;
  }
  

  @Override
  public SuccessfulResponse deleteUser(Integer id) {
    if (!this.userRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Kh??ng t??m th???y ng?????i d??ng n??o v???i id l?? " + id);
    }

    User user = this.userRepository.findById(id).get();
    user.setDeletedAt(new Date());
    
    user.setUsername(user.getUsername() + "$" + UUID.randomUUID());
    
    this.userRepository.save(user);

    SuccessfulResponse response = new SuccessfulResponse();
    response.addMessage("X??a ng?????i d??ng th??nh c??ng");

    return response;
  }
  
  @Override
  public LoginResponse authenticate(LoginRequest request) {
    User user = this.userRepository.getUserByUsername(request.getUsername());

    if (user == null) {
      throw ServiceExceptionFactory.unauthorized()
              .addMessage("T??n ????ng nh???p ho???c m???t kh???u kh??ng ????ng");
    }

    if (!user.getPassword().equals(request.getPassword())) {
      throw ServiceExceptionFactory.unauthorized()
              .addMessage("T??n ????ng nh???p ho???c m???t kh???u kh??ng ????ng");
    }

    String accessToken = this.jwtTokenProvider.generateToken(user);

    return new LoginResponse(new UserResponse(user), accessToken);
  }
  
}
  