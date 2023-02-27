package com.hcmute.project.instagram.backend.controller.common;

import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.CreateTreeRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.GetTreeResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.ListTreeResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.UpdateTreeRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.interfaces.TreeService;
import com.hcmute.project.instagram.backend.domain.base.ResponseBaseAbstract;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
//hoi lai, camelcase hay la a-a-a
@RequestMapping("api/common/tree")
public class CommonTreeController {

  @Autowired
  private TreeService treeService;

  public CommonTreeController() {

  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract searchTree() {
    ListTreeResponse listTreeResponse = this.treeService.listTrees();
    return listTreeResponse;
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract getTree(
    @PathVariable Integer id
  ) {
    GetTreeResponse getTreeResponse = this.treeService.getTreeById(id);
    return getTreeResponse;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseBaseAbstract createTree(
    @RequestBody @Valid CreateTreeRequest request
  ) {
    SuccessfulResponse createTreeResponse = this.treeService.createTree(request);
    return createTreeResponse;
  }
  
  @PutMapping("{id}/update")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract updateTree(
    @PathVariable Integer id,
    @RequestBody @Valid UpdateTreeRequest request
  ) {
    request.setTreeId(id);
    SuccessfulResponse updateTreeResponse = this.treeService.updateTree(request);
    return updateTreeResponse;
  }

  @DeleteMapping("{id}/delete")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract deleteTree(
    @PathVariable Integer id
  ) {
    SuccessfulResponse updateTreeResponse = this.treeService.deleteTree(id);
    return updateTreeResponse;
  }
}
