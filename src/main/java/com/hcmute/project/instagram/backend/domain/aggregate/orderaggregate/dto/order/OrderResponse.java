package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order;

import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.Order;
import lombok.Data;

import java.util.Date;

/**
  * Data transfer object (DTO) for Order
*/
@Data
public class OrderResponse {
  
  private Integer id;
  
  private Date createdAt;
  
  private Date lastUpdatedAt;
  
  private Date deletedAt;
  
  private String note;
  
  private Integer customerId;
  

  public OrderResponse(Order order) {
    
    this.id = order.getId();
    this.createdAt = order.getCreatedAt();
    this.lastUpdatedAt = order.getLastUpdatedAt();
    this.deletedAt = order.getDeletedAt();
    this.note = order.getNote();
    if (order.getCustomer() != null) {
      this.customerId = order.getCustomer().getId();
    }
    
  }
}