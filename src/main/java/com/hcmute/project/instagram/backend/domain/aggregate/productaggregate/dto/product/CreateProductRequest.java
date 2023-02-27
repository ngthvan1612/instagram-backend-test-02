package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product;

import lombok.Data;

@Data
public class CreateProductRequest {
  
  
  private String productName;
  
  
  private Integer price;
  
  
  private Integer discount;
  
  private Integer productCategoryId;
  
}