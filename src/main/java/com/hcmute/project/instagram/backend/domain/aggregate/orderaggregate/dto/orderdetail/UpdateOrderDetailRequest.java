package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateOrderDetailRequest {
  @JsonIgnore
  private Integer orderDetailId;
  
  private Integer price;
  private Integer discount;
  private Integer quantity;
  
  private Integer orderId;
  private Integer productId;
}
