package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public class GetOrderResponse extends SuccessfulResponse {
  public GetOrderResponse(OrderResponse orderResponse) {
    super();
    this.setData(orderResponse);
  }
}
