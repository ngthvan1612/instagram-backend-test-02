package com.hcmute.project.instagram.backend.controller.admin;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.CreateProductCategoryRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.GetProductCategoryResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.ListProductCategoryResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.productcategory.UpdateProductCategoryRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces.ProductCategoryService;
import com.hcmute.project.instagram.backend.domain.base.ResponseBaseAbstract;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
