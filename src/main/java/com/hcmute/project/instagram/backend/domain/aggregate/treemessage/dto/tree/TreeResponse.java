package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree;

import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.entities.Tree;
import lombok.Data;

import java.util.Date;

/**
  * Data transfer object (DTO) for Tree
*/
@Data
public class TreeResponse {
  
  private Integer id;
  
  private Date createdAt;
  
  private Date lastUpdatedAt;
  
  private Date deletedAt;
  
  private String message;
  
  private Integer senderId;
  

  public TreeResponse(Tree tree) {
    
    this.id = tree.getId();
    this.createdAt = tree.getCreatedAt();
    this.lastUpdatedAt = tree.getLastUpdatedAt();
    this.deletedAt = tree.getDeletedAt();
    this.message = tree.getMessage();
    if (tree.getSender() != null) {
      this.senderId = tree.getSender().getId();
    }
    
  }
}