package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services;

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
public class TreeServiceImpl implements TreeService {
  
  @Autowired
  private TreeRepository treeRepository;
  
  @Autowired
  private StorageRepository storageRepository;

  public TreeServiceImpl() {

  }

  //TODO: Validate with annotation
  //TODO: check fk before create & update
  //TODO: update unique column for delete
  //TODO: swagger
  //TODO: authorize
  //TODO: hash password
  //TODO: loggggggggg

  @Override
  public SuccessfulResponse createTree(CreateTreeRequest request) {
    //Validate
    

    //Check null
    
    Optional<Tree> optionalSender = this.treeRepository.findById(request.getSenderId());
    Tree sender = null;
    
    if (optionalSender.isPresent())
      sender = optionalSender.get();
    
    
    Tree tree = new Tree();
    
    tree.setMessage(request.getMessage());
    tree.setSender(sender);

    //Save to database
    this.treeRepository.save(tree);

    //Return
    TreeResponse treeDTO = new TreeResponse(tree);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(treeDTO);
    response.addMessage("Tạo Cây thành công");

    return response;
  }

  @Override
  public GetTreeResponse getTreeById(Integer id) {
    if (!this.treeRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy Cây nào với id là " + id);
    }

    Tree tree = this.treeRepository.findById(id).get();
    TreeResponse treeDTO = new TreeResponse(tree);
    GetTreeResponse response = new GetTreeResponse(treeDTO);

    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public ListTreeResponse searchTrees(Map<String, String> queries) {
    List<TreeResponse> listTreeResponses = this.treeRepository.searchTree(queries)
          .stream().map(tree -> new TreeResponse(tree)).toList();
    
    ListTreeResponse response = new ListTreeResponse(listTreeResponses);
    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public SuccessfulResponse updateTree(UpdateTreeRequest request) {
    //Check record exists
    if (!this.treeRepository.existsById(request.getTreeId())) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy Cây nào với id là " + request.getTreeId());
    }

    //Read data from request
    Tree tree = this.treeRepository.findById(request.getTreeId()).get();
    
    Optional<Tree> optionalSender = this.treeRepository.findById(request.getSenderId());
    Tree sender = null;
    
    if (optionalSender.isPresent()) {
      sender = optionalSender.get();
    }
    
    
    
    tree.setMessage(request.getMessage());
    tree.setSender(sender);

    //Validate unique
    

    //Update last changed time
    tree.setLastUpdatedAt(new Date());

    //Store
    this.treeRepository.save(tree);

    //Return
    TreeResponse treeDTO = new TreeResponse(tree);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(treeDTO);
    response.addMessage("Cập nhật Cây thành công");

    return response;
  }
  

  @Override
  public SuccessfulResponse deleteTree(Integer id) {
    if (!this.treeRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy Cây nào với id là " + id);
    }

    Tree tree = this.treeRepository.findById(id).get();
    tree.setDeletedAt(new Date());
    
    this.treeRepository.save(tree);

    SuccessfulResponse response = new SuccessfulResponse();
    response.addMessage("Xóa Cây thành công");

    return response;
  }
  
}
  