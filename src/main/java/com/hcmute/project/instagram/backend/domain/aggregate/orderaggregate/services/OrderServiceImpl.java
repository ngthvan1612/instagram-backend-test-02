package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services;

import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.order.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.Order;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.repositories.OrderRepository;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces.OrderService;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities.User;
import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.repositories.UserRepository;
import com.hcmute.project.instagram.backend.domain.base.StorageRepository;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import com.hcmute.project.instagram.backend.domain.exception.ServiceExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
  public ListOrderResponse listOrders() {
    List<OrderResponse> listOrderResponses = this.orderRepository.findAll()
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
  