package com.hcmute.project.instagram.backend.controller.admin;

import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.CreateOrderRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.GetOrderResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.ListOrderResponse;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.UpdateOrderRequest;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces.OrderService;
import com.hcmute.project.instagram.backend.domain.base.ResponseBaseAbstract;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
//hoi lai, camelcase hay la a-a-a
@RequestMapping("api/admin/order")
public class AdminOrderController {

  @Autowired
  private OrderService orderService;

  public AdminOrderController() {

  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract searchOrder() {
    ListOrderResponse listOrderResponse = this.orderService.listOrders();
    return listOrderResponse;
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract getOrder(
    @PathVariable Integer id
  ) {
    GetOrderResponse getOrderResponse = this.orderService.getOrderById(id);
    return getOrderResponse;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseBaseAbstract createOrder(
    @RequestBody CreateOrderRequest request
  ) {
    SuccessfulResponse createOrderResponse = this.orderService.createOrder(request);
    return createOrderResponse;
  }
  
  @PutMapping("{id}/update")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract updateOrder(
    @PathVariable Integer id,
    @RequestBody UpdateOrderRequest request
  ) {
    request.setOrderId(id);
    SuccessfulResponse updateOrderResponse = this.orderService.updateOrder(request);
    return updateOrderResponse;
  }

  @DeleteMapping("{id}/delete")
  @ResponseStatus(HttpStatus.OK)
  public ResponseBaseAbstract deleteOrder(
    @PathVariable Integer id
  ) {
    SuccessfulResponse updateOrderResponse = this.orderService.deleteOrder(id);
    return updateOrderResponse;
  }
}
