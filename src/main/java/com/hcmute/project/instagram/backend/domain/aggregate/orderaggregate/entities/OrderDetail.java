package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tbl_orderDetail")
public class OrderDetail {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(name = "createdAt")
  private Date createdAt = new Date();
  
  @Column(name = "lastUpdatedAt")
  private Date lastUpdatedAt = new Date();
  
  @Column(name = "deletedAt")
  private Date deletedAt;
  
  @Column(name = "price")
  private Integer price;
  
  @Column(name = "discount")
  private Integer discount;
  
  @Column(name = "quantity")
  private Integer quantity;
  
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
  
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  
}
