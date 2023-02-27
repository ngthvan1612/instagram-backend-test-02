package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.ProductCategory;
import lombok.Data;

import java.util.Date;

/**
  * Data transfer object (DTO) for ProductCategory
*/
@Data
public class ProductCategoryResponse {
  
  private Integer id;
  
  private Date createdAt;
  
  private Date lastUpdatedAt;
  
  private Date deletedAt;
  
  private String categoryName;
  

  public ProductCategoryResponse(ProductCategory productCategory) {
    
    this.id = productCategory.getId();
    this.createdAt = productCategory.getCreatedAt();
    this.lastUpdatedAt = productCategory.getLastUpdatedAt();
    this.deletedAt = productCategory.getDeletedAt();
    this.categoryName = productCategory.getCategoryName();
  }
}