package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.Product;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.ProductCategory;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories.ProductCategoryRepository;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories.ProductRepository;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces.ProductService;
import com.hcmute.project.instagram.backend.domain.base.StorageRepository;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import com.hcmute.project.instagram.backend.domain.exception.ServiceExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
  public ListProductResponse listProducts() {
    List<ProductResponse> listProductResponses = this.productRepository.findAll()
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
  