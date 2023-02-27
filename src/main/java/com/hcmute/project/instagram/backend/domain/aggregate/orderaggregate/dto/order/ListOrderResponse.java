package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

import java.util.List;

public class ListOrderResponse extends SuccessfulResponse {
  public ListOrderResponse(List<OrderResponse> orderResponses) {
    super();
    this.setData(orderResponses);
  }
}
