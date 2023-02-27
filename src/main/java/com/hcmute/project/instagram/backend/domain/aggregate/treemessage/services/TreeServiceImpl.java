package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services;

import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.entities.Tree;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.repositories.TreeRepository;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.interfaces.TreeService;
import com.hcmute.project.instagram.backend.domain.base.StorageRepository;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import com.hcmute.project.instagram.backend.domain.exception.ServiceExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
  public ListTreeResponse listTrees() {
    List<TreeResponse> listTreeResponses = this.treeRepository.findAll()
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
  