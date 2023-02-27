package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.ProductCategory;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories.ProductCategoryRepository;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces.ProductCategoryService;
import com.hcmute.project.instagram.backend.domain.base.StorageRepository;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import com.hcmute.project.instagram.backend.domain.exception.ServiceExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
  public ListProductCategoryResponse listProductCategorys() {
    List<ProductCategoryResponse> listProductCategoryResponses = this.productCategoryRepository.findAll()
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
  