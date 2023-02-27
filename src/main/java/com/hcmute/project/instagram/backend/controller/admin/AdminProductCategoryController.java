package com.hcmute.project.instagram.backend.controller.admin;

import com.hcmute.project.instagram.backend.*;
import com.hcmute.project.instagram.backend.controller.admin.*;
import com.hcmute.project.instagram.backend.controller.common.*;
import com.hcmute.project.instagram.backend.controller.exception.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.dto.tree.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.treemessage.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.enums.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.repositories.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces.*;
import com.hcmute.project.instagram.backend.domain.base.*;
import com.hcmute.project.instagram.backend.domain.exception.*;
import com.hcmute.project.instagram.backend.infrastructure.aws.minio.*;
import com.hcmute.project.instagram.backend.jwt.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

@CrossOrigin("*")
@RestController
//hoi lai, camelcase hay la a-a-a
@RequestMapping("api/admin/product-category")
public class AdminProductCategoryController {

  @Autowired
  private ProductCategoryService productCategoryService;

  public AdminProductCategoryController() {

  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract searchProductCategory() {
    ListProductCategoryResponse listProductCategoryResponse = this.productCategoryService.listProductCategorys();
    return listProductCategoryResponse;
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract getProductCategory(
    @PathVariable Integer id
  ) {
    GetProductCategoryResponse getProductCategoryResponse = this.productCategoryService.getProductCategoryById(id);
    return getProductCategoryResponse;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseBaseAbstract createProductCategory(
    @RequestBody CreateProductCategoryRequest request
  ) {
    SuccessfulResponse createProductCategoryResponse = this.productCategoryService.createProductCategory(request);
    return createProductCategoryResponse;
  }
  
  @PutMapping("{id}/update")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract updateProductCategory(
    @PathVariable Integer id,
    @RequestBody UpdateProductCategoryRequest request
  ) {
    request.setProductCategoryId(id);
    SuccessfulResponse updateProductCategoryResponse = this.productCategoryService.updateProductCategory(request);
    return updateProductCategoryResponse;
  }

  @DeleteMapping("{id}/delete")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract deleteProductCategory(
    @PathVariable Integer id
  ) {
    SuccessfulResponse updateProductCategoryResponse = this.productCategoryService.deleteProductCategory(id);
    return updateProductCategoryResponse;
  }
}
