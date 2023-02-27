package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateOrderRequest {
  @JsonIgnore
  private Integer orderId;
  
  private String note;
  
  private Integer customerId;
}
