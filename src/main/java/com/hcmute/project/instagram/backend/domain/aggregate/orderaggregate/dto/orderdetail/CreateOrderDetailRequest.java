package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail;

import lombok.Data;

@Data
public class CreateOrderDetailRequest {
  
  
  private Integer price;
  
  
  private Integer discount;
  
  
  private Integer quantity;
  
  private Integer orderId;
  private Integer productId;
  
}