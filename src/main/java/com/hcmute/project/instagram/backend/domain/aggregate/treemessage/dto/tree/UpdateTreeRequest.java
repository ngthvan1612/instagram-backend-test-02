package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateTreeRequest {
  @JsonIgnore
  private Integer treeId;
  
  private String message;
  
  private Integer senderId;
}
