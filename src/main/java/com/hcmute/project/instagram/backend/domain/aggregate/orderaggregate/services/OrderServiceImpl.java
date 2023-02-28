package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services;

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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderServiceImpl implements OrderService {
  
  @Autowired
  private OrderRepository orderRepository;
  
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private StorageRepository storageRepository;

  public OrderServiceImpl() {

  }

  //TODO: Validate with annotation
  //TODO: check fk before create & update
  //TODO: update unique column for delete
  //TODO: swagger
  //TODO: authorize
  //TODO: hash password
  //TODO: loggggggggg

  @Override
  public SuccessfulResponse createOrder(CreateOrderRequest request) {
    //Validate
    

    //Check null
    
    Optional<User> optionalCustomer = this.userRepository.findById(request.getCustomerId());
    User customer = null;
    
    if (optionalCustomer.isEmpty()) {
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Không tồn tại người dùng nào với customerId = " + request.getCustomerId());
    }
    else {
      customer = optionalCustomer.get();
    }
    
    
    Order order = new Order();
    
    order.setNote(request.getNote());
    order.setCustomer(customer);

    //Save to database
    this.orderRepository.save(order);

    //Return
    OrderResponse orderDTO = new OrderResponse(order);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(orderDTO);
    response.addMessage("Tạo giỏ hàng thành công");

    return response;
  }

  @Override
  public GetOrderResponse getOrderById(Integer id) {
    if (!this.orderRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy giỏ hàng nào với id là " + id);
    }

    Order order = this.orderRepository.findById(id).get();
    OrderResponse orderDTO = new OrderResponse(order);
    GetOrderResponse response = new GetOrderResponse(orderDTO);

    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public ListOrderResponse searchOrders(Map<String, String> queries) {
    List<OrderResponse> listOrderResponses = this.orderRepository.searchOrder(queries)
          .stream().map(order -> new OrderResponse(order)).toList();
    
    ListOrderResponse response = new ListOrderResponse(listOrderResponses);
    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public SuccessfulResponse updateOrder(UpdateOrderRequest request) {
    //Check record exists
    if (!this.orderRepository.existsById(request.getOrderId())) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy giỏ hàng nào với id là " + request.getOrderId());
    }

    //Read data from request
    Order order = this.orderRepository.findById(request.getOrderId()).get();
    
    Optional<User> optionalCustomer = this.userRepository.findById(request.getCustomerId());
    User customer = null;
    
    if (optionalCustomer.isEmpty()) { 
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Không tồn tại giỏ hàng nào với customerId = " + request.getCustomerId());
    }
    else {
      customer = optionalCustomer.get();
    }
    
    
    
    order.setNote(request.getNote());
    order.setCustomer(customer);

    //Validate unique
    

    //Update last changed time
    order.setLastUpdatedAt(new Date());

    //Store
    this.orderRepository.save(order);

    //Return
    OrderResponse orderDTO = new OrderResponse(order);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(orderDTO);
    response.addMessage("Cập nhật giỏ hàng thành công");

    return response;
  }
  

  @Override
  public SuccessfulResponse deleteOrder(Integer id) {
    if (!this.orderRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy giỏ hàng nào với id là " + id);
    }

    Order order = this.orderRepository.findById(id).get();
    order.setDeletedAt(new Date());
    
    this.orderRepository.save(order);

    SuccessfulResponse response = new SuccessfulResponse();
    response.addMessage("Xóa giỏ hàng thành công");

    return response;
  }
  
}
  