package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "tbl_tree")
public class Tree {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(name = "createdAt")
  private Date createdAt = new Date();
  
  @Column(name = "lastUpdatedAt")
  private Date lastUpdatedAt = new Date();
  
  @Column(name = "deletedAt")
  private Date deletedAt;
  
  @Column(name = "message")
  private String message;
  
  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Tree sender;
  
  @OneToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  private Collection<Tree> trees;
  
}
