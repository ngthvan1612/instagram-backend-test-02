package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

import java.util.List;

public class ListProductResponse extends SuccessfulResponse {
  public ListProductResponse(List<ProductResponse> productResponses) {
    super();
    this.setData(productResponses);
  }
}
