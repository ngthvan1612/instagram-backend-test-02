package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public class GetProductCategoryResponse extends SuccessfulResponse {
  public GetProductCategoryResponse(ProductCategoryResponse productCategoryResponse) {
    super();
    this.setData(productCategoryResponse);
  }
}
