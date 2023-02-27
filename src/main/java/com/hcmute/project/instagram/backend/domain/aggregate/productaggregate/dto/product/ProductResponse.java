package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.Product;
import lombok.Data;

import java.util.Date;

/**
  * Data transfer object (DTO) for Product
*/
@Data
public class ProductResponse {
  
  private Integer id;
  
  private Date createdAt;
  
  private Date lastUpdatedAt;
  
  private Date deletedAt;
  
  private String productName;
  
  private Integer price;
  
  private Integer discount;
  
  private Integer productCategoryId;
  

  public ProductResponse(Product product) {
    
    this.id = product.getId();
    this.createdAt = product.getCreatedAt();
    this.lastUpdatedAt = product.getLastUpdatedAt();
    this.deletedAt = product.getDeletedAt();
    this.productName = product.getProductName();
    this.price = product.getPrice();
    this.discount = product.getDiscount();
    if (product.getProductCategory() != null) {
      this.productCategoryId = product.getProductCategory().getId();
    }
    
  }
}