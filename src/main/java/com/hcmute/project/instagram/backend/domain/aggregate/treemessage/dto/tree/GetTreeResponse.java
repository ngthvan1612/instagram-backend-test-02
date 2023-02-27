package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public class GetTreeResponse extends SuccessfulResponse {
  public GetTreeResponse(TreeResponse treeResponse) {
    super();
    this.setData(treeResponse);
  }
}
