package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_order")
public class Order {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(name = "createdAt")
  private Date createdAt = new Date();
  
  @Column(name = "lastUpdatedAt")
  private Date lastUpdatedAt = new Date();
  
  @Column(name = "deletedAt")
  private Date deletedAt;
  
  @Column(name = "note")
  private String note;
  
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private User customer;
  
  @OneToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  private Collection<OrderDetail> orderDetails;
  
}
