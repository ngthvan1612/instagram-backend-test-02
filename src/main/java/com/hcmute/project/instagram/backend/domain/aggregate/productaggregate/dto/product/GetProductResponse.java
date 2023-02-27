package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public class GetProductResponse extends SuccessfulResponse {
  public GetProductResponse(ProductResponse productResponse) {
    super();
    this.setData(productResponse);
  }
}
