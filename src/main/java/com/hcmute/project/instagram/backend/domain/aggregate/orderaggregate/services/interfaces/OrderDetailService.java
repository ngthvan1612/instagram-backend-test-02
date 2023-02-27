package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces;

import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.CreateOrderDetailRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.GetOrderDetailResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.ListOrderDetailResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.UpdateOrderDetailRequest;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public interface OrderDetailService {
  SuccessfulResponse createOrderDetail(CreateOrderDetailRequest request);
  GetOrderDetailResponse getOrderDetailById(Integer id);
  ListOrderDetailResponse listOrderDetails();
  SuccessfulResponse updateOrderDetail(UpdateOrderDetailRequest request);
  SuccessfulResponse deleteOrderDetail(Integer id);

}
