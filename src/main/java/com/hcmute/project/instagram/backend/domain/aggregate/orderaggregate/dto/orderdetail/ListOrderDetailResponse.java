package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

import java.util.List;

public class ListOrderDetailResponse extends SuccessfulResponse {
  public ListOrderDetailResponse(List<OrderDetailResponse> orderDetailResponses) {
    super();
    this.setData(orderDetailResponses);
  }
}
