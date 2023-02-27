package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail;

import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.OrderDetail;
import lombok.Data;

import java.util.Date;

/**
  * Data transfer object (DTO) for OrderDetail
*/
@Data
public class OrderDetailResponse {
  
  private Integer id;
  
  private Date createdAt;
  
  private Date lastUpdatedAt;
  
  private Date deletedAt;
  
  private Integer price;
  
  private Integer discount;
  
  private Integer quantity;
  
  private Integer orderId;
  
  private Integer productId;
  

  public OrderDetailResponse(OrderDetail orderDetail) {
    
    this.id = orderDetail.getId();
    this.createdAt = orderDetail.getCreatedAt();
    this.lastUpdatedAt = orderDetail.getLastUpdatedAt();
    this.deletedAt = orderDetail.getDeletedAt();
    this.price = orderDetail.getPrice();
    this.discount = orderDetail.getDiscount();
    this.quantity = orderDetail.getQuantity();
    if (orderDetail.getOrder() != null) {
      this.orderId = orderDetail.getOrder().getId();
    }
    
    if (orderDetail.getProduct() != null) {
      this.productId = orderDetail.getProduct().getId();
    }
    
  }
}