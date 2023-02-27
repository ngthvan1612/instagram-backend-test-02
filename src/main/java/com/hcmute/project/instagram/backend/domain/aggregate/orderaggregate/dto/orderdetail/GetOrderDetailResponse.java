package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public class GetOrderDetailResponse extends SuccessfulResponse {
  public GetOrderDetailResponse(OrderDetailResponse orderDetailResponse) {
    super();
    this.setData(orderDetailResponse);
  }
}
