package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces;

import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.CreateOrderRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.GetOrderResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.ListOrderResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.UpdateOrderRequest;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public interface OrderService {
  SuccessfulResponse createOrder(CreateOrderRequest request);
  GetOrderResponse getOrderById(Integer id);
  ListOrderResponse listOrders();
  SuccessfulResponse updateOrder(UpdateOrderRequest request);
  SuccessfulResponse deleteOrder(Integer id);

}
