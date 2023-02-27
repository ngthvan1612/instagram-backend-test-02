package com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.interfaces;

import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.CreateTreeRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.GetTreeResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.ListTreeResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.UpdateTreeRequest;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

public interface TreeService {
  SuccessfulResponse createTree(CreateTreeRequest request);
  GetTreeResponse getTreeById(Integer id);
  ListTreeResponse listTrees();
  SuccessfulResponse updateTree(UpdateTreeRequest request);
  SuccessfulResponse deleteTree(Integer id);

}
