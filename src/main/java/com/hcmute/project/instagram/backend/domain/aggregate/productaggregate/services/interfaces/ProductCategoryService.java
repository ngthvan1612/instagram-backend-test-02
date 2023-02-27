package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.CreateProductCategoryRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.GetProductCategoryResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.ListProductCategoryResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.UpdateProductCategoryRequest;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public interface ProductCategoryService {
  SuccessfulResponse createProductCategory(CreateProductCategoryRequest request);
  GetProductCategoryResponse getProductCategoryById(Integer id);
  ListProductCategoryResponse listProductCategorys();
  SuccessfulResponse updateProductCategory(UpdateProductCategoryRequest request);
  SuccessfulResponse deleteProductCategory(Integer id);

}
