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
public class ProductServiceImpl implements ProductService {
  
  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private ProductCategoryRepository productCategoryRepository;
  @Autowired
  private StorageRepository storageRepository;

  public ProductServiceImpl() {

  }

  //TODO: Validate with annotation
  //TODO: check fk before create & update
  //TODO: update unique column for delete
  //TODO: swagger
  //TODO: authorize
  //TODO: hash password
  //TODO: loggggggggg

  @Override
  public SuccessfulResponse createProduct(CreateProductRequest request) {
    //Validate
    

    //Check null
    
    Optional<ProductCategory> optionalProductCategory = this.productCategoryRepository.findById(request.getProductCategoryId());
    ProductCategory productCategory = null;
    
    if (optionalProductCategory.isEmpty()) {
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Không tồn tại loại sản phẩm nào với productCategoryId = " + request.getProductCategoryId());
    }
    else {
      productCategory = optionalProductCategory.get();
    }
    
    
    Product product = new Product();
    
    product.setProductName(request.getProductName());
    product.setPrice(request.getPrice());
    product.setDiscount(request.getDiscount());
    product.setProductCategory(productCategory);

    //Save to database
    this.productRepository.save(product);

    //Return
    ProductResponse productDTO = new ProductResponse(product);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(productDTO);
    response.addMessage("Tạo sản phẩm thành công");

    return response;
  }

  @Override
  public GetProductResponse getProductById(Integer id) {
    if (!this.productRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy sản phẩm nào với id là " + id);
    }

    Product product = this.productRepository.findById(id).get();
    ProductResponse productDTO = new ProductResponse(product);
    GetProductResponse response = new GetProductResponse(productDTO);

    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public ListProductResponse searchProducts(Map<String, String> queries) {
    List<ProductResponse> listProductResponses = this.productRepository.searchProduct(queries)
          .stream().map(product -> new ProductResponse(product)).toList();
    
    ListProductResponse response = new ListProductResponse(listProductResponses);
    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public SuccessfulResponse updateProduct(UpdateProductRequest request) {
    //Check record exists
    if (!this.productRepository.existsById(request.getProductId())) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy sản phẩm nào với id là " + request.getProductId());
    }

    //Read data from request
    Product product = this.productRepository.findById(request.getProductId()).get();
    
    Optional<ProductCategory> optionalProductCategory = this.productCategoryRepository.findById(request.getProductCategoryId());
    ProductCategory productCategory = null;
    
    if (optionalProductCategory.isEmpty()) { 
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Không tồn tại sản phẩm nào với productCategoryId = " + request.getProductCategoryId());
    }
    else {
      productCategory = optionalProductCategory.get();
    }
    
    
    
    product.setProductName(request.getProductName());
    product.setPrice(request.getPrice());
    product.setDiscount(request.getDiscount());
    product.setProductCategory(productCategory);

    //Validate unique
    

    //Update last changed time
    product.setLastUpdatedAt(new Date());

    //Store
    this.productRepository.save(product);

    //Return
    ProductResponse productDTO = new ProductResponse(product);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(productDTO);
    response.addMessage("Cập nhật sản phẩm thành công");

    return response;
  }
  

  @Override
  public SuccessfulResponse deleteProduct(Integer id) {
    if (!this.productRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy sản phẩm nào với id là " + id);
    }

    Product product = this.productRepository.findById(id).get();
    product.setDeletedAt(new Date());
    
    this.productRepository.save(product);

    SuccessfulResponse response = new SuccessfulResponse();
    response.addMessage("Xóa sản phẩm thành công");

    return response;
  }
  
}
  