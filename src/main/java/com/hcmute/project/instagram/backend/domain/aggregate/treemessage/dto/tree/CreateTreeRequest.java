package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree;

import lombok.Data;

@Data
public class CreateTreeRequest {
  
  
  private String message;
  
  private Integer senderId;
  
}