package com.hcmute.project.instagram.backend.controller.common;

import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.CreateProductRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.GetProductResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.ListProductResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.dto.product.UpdateProductRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.services.interfaces.ProductService;
import com.hcmute.project.instagram.backend.domain.base.ResponseBaseAbstract;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
//hoi lai, camelcase hay la a-a-a
@RequestMapping("api/common/product")
public class CommonProductController {

  @Autowired
  private ProductService productService;

  public CommonProductController() {

  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract searchProduct() {
    ListProductResponse listProductResponse = this.productService.listProducts();
    return listProductResponse;
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract getProduct(
    @PathVariable Integer id
  ) {
    GetProductResponse getProductResponse = this.productService.getProductById(id);
    return getProductResponse;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseBaseAbstract createProduct(
    @RequestBody @Valid CreateProductRequest request
  ) {
    SuccessfulResponse createProductResponse = this.productService.createProduct(request);
    return createProductResponse;
  }
  
  @PutMapping("{id}/update")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract updateProduct(
    @PathVariable Integer id,
    @RequestBody @Valid UpdateProductRequest request
  ) {
    request.setProductId(id);
    SuccessfulResponse updateProductResponse = this.productService.updateProduct(request);
    return updateProductResponse;
  }

  @DeleteMapping("{id}/delete")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract deleteProduct(
    @PathVariable Integer id
  ) {
    SuccessfulResponse updateProductResponse = this.productService.deleteProduct(id);
    return updateProductResponse;
  }
}
