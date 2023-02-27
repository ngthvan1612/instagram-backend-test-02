package com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_productCategory")
public class ProductCategory {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(name = "createdAt")
  private Date createdAt = new Date();
  
  @Column(name = "lastUpdatedAt")
  private Date lastUpdatedAt = new Date();
  
  @Column(name = "deletedAt")
  private Date deletedAt;
  
  @Column(name = "categoryName")
  private String categoryName;
  
  @OneToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  private Collection<Product> products;
  
}
