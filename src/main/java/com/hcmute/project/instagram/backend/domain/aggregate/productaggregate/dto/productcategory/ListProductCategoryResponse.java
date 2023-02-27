package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

import java.util.List;

public class ListProductCategoryResponse extends SuccessfulResponse {
  public ListProductCategoryResponse(List<ProductCategoryResponse> productCategoryResponses) {
    super();
    this.setData(productCategoryResponses);
  }
}
