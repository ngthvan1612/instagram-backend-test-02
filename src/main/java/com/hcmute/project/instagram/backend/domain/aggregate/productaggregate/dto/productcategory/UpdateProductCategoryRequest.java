package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateProductCategoryRequest {
  @JsonIgnore
  private Integer productCategoryId;
  
  private String categoryName;
  
}
