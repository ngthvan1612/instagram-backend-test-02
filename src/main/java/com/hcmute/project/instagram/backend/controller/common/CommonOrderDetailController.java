package com.hcmute.project.instagram.backend.controller.common;

import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.CreateOrderDetailRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.GetOrderDetailResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.ListOrderDetailResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.UpdateOrderDetailRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces.OrderDetailService;
import com.hcmute.project.instagram.backend.domain.base.ResponseBaseAbstract;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
//hoi lai, camelcase hay la a-a-a
@RequestMapping("api/common/order-detail")
public class CommonOrderDetailController {

  @Autowired
  private OrderDetailService orderDetailService;

  public CommonOrderDetailController() {

  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract searchOrderDetail() {
    ListOrderDetailResponse listOrderDetailResponse = this.orderDetailService.listOrderDetails();
    return listOrderDetailResponse;
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract getOrderDetail(
    @PathVariable Integer id
  ) {
    GetOrderDetailResponse getOrderDetailResponse = this.orderDetailService.getOrderDetailById(id);
    return getOrderDetailResponse;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseBaseAbstract createOrderDetail(
    @RequestBody @Valid CreateOrderDetailRequest request
  ) {
    SuccessfulResponse createOrderDetailResponse = this.orderDetailService.createOrderDetail(request);
    return createOrderDetailResponse;
  }
  
  @PutMapping("{id}/update")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract updateOrderDetail(
    @PathVariable Integer id,
    @RequestBody @Valid UpdateOrderDetailRequest request
  ) {
    request.setOrderDetailId(id);
    SuccessfulResponse updateOrderDetailResponse = this.orderDetailService.updateOrderDetail(request);
    return updateOrderDetailResponse;
  }

  @DeleteMapping("{id}/delete")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract deleteOrderDetail(
    @PathVariable Integer id
  ) {
    SuccessfulResponse updateOrderDetailResponse = this.orderDetailService.deleteOrderDetail(id);
    return updateOrderDetailResponse;
  }
}
