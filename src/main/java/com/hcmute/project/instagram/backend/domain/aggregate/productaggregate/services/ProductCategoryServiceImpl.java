package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services;

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
public class ProductCategoryServiceImpl implements ProductCategoryService {
  
  @Autowired
  private ProductCategoryRepository productCategoryRepository;
  
  @Autowired
  private StorageRepository storageRepository;

  public ProductCategoryServiceImpl() {

  }

  //TODO: Validate with annotation
  //TODO: check fk before create & update
  //TODO: update unique column for delete
  //TODO: swagger
  //TODO: authorize
  //TODO: hash password
  //TODO: loggggggggg

  @Override
  public SuccessfulResponse createProductCategory(CreateProductCategoryRequest request) {
    //Validate
    if (this.productCategoryRepository.existsByCategoryName(request.getCategoryName())) {
      throw ServiceExceptionFactory.duplicate()
        .addMessage("Đã tồn tại loại sản phẩm với categoryName là " + request.getCategoryName());
    }

    //Check null
    
    ProductCategory productCategory = new ProductCategory();
    
    productCategory.setCategoryName(request.getCategoryName());

    //Save to database
    this.productCategoryRepository.save(productCategory);

    //Return
    ProductCategoryResponse productCategoryDTO = new ProductCategoryResponse(productCategory);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(productCategoryDTO);
    response.addMessage("Tạo loại sản phẩm thành công");

    return response;
  }

  @Override
  public GetProductCategoryResponse getProductCategoryById(Integer id) {
    if (!this.productCategoryRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy loại sản phẩm nào với id là " + id);
    }

    ProductCategory productCategory = this.productCategoryRepository.findById(id).get();
    ProductCategoryResponse productCategoryDTO = new ProductCategoryResponse(productCategory);
    GetProductCategoryResponse response = new GetProductCategoryResponse(productCategoryDTO);

    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public ListProductCategoryResponse searchProductCategorys(Map<String, String> queries) {
    List<ProductCategoryResponse> listProductCategoryResponses = this.productCategoryRepository.searchProductCategory(queries)
          .stream().map(productCategory -> new ProductCategoryResponse(productCategory)).toList();
    
    ListProductCategoryResponse response = new ListProductCategoryResponse(listProductCategoryResponses);
    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public SuccessfulResponse updateProductCategory(UpdateProductCategoryRequest request) {
    //Check record exists
    if (!this.productCategoryRepository.existsById(request.getProductCategoryId())) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy loại sản phẩm nào với id là " + request.getProductCategoryId());
    }

    //Read data from request
    ProductCategory productCategory = this.productCategoryRepository.findById(request.getProductCategoryId()).get();
    
    
    productCategory.setCategoryName(request.getCategoryName());

    //Validate unique
    
    if (this.productCategoryRepository.existsByCategoryNameExceptId(request.getCategoryName(), request.getProductCategoryId())) {
      throw ServiceExceptionFactory.duplicate()
        .addMessage("Đã tồn tại loại sản phẩm với tên loại sản phẩm là " + request.getCategoryName());
    }
    

    //Update last changed time
    productCategory.setLastUpdatedAt(new Date());

    //Store
    this.productCategoryRepository.save(productCategory);

    //Return
    ProductCategoryResponse productCategoryDTO = new ProductCategoryResponse(productCategory);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(productCategoryDTO);
    response.addMessage("Cập nhật loại sản phẩm thành công");

    return response;
  }
  

  @Override
  public SuccessfulResponse deleteProductCategory(Integer id) {
    if (!this.productCategoryRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy loại sản phẩm nào với id là " + id);
    }

    ProductCategory productCategory = this.productCategoryRepository.findById(id).get();
    productCategory.setDeletedAt(new Date());
    
    productCategory.setCategoryName(productCategory.getCategoryName() + "$" + UUID.randomUUID());
    
    this.productCategoryRepository.save(productCategory);

    SuccessfulResponse response = new SuccessfulResponse();
    response.addMessage("Xóa loại sản phẩm thành công");

    return response;
  }
  
}
  