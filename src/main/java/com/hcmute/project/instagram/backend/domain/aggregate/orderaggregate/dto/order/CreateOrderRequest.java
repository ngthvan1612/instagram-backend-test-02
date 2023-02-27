package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order;

import lombok.Data;

@Data
public class CreateOrderRequest {
  
  
  private String note;
  
  private Integer customerId;
  
}