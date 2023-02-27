package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.OrderDetail;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_product")
public class Product {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(name = "createdAt")
  private Date createdAt = new Date();
  
  @Column(name = "lastUpdatedAt")
  private Date lastUpdatedAt = new Date();
  
  @Column(name = "deletedAt")
  private Date deletedAt;
  
  @Column(name = "productName")
  private String productName;
  
  @Column(name = "price")
  private Integer price;
  
  @Column(name = "discount")
  private Integer discount;
  
  @ManyToOne
  @JoinColumn(name = "productCategory_id")
  private ProductCategory productCategory;
  
  @OneToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  private Collection<OrderDetail> orderDetails;
  
}
