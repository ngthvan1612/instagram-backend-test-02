package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateProductRequest {
  @JsonIgnore
  private Integer productId;
  
  private String productName;
  private Integer price;
  private Integer discount;
  
  private Integer productCategoryId;
}
