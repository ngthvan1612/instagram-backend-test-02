package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.CreateProductRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.GetProductResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.ListProductResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.UpdateProductRequest;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public interface ProductService {
  SuccessfulResponse createProduct(CreateProductRequest request);
  GetProductResponse getProductById(Integer id);
  ListProductResponse listProducts();
  SuccessfulResponse updateProduct(UpdateProductRequest request);
  SuccessfulResponse deleteProduct(Integer id);

}
