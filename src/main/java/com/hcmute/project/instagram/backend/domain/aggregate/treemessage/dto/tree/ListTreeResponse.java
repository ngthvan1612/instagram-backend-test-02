package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

import java.util.List;

public class ListTreeResponse extends SuccessfulResponse {
  public ListTreeResponse(List<TreeResponse> treeResponses) {
    super();
    this.setData(treeResponses);
  }
}
