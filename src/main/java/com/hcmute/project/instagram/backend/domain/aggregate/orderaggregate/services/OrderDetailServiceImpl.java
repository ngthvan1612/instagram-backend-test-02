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
public class OrderDetailServiceImpl implements OrderDetailService {
  
  @Autowired
  private OrderDetailRepository orderDetailRepository;
  
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private StorageRepository storageRepository;

  public OrderDetailServiceImpl() {

  }

  //TODO: Validate with annotation
  //TODO: check fk before create & update
  //TODO: update unique column for delete
  //TODO: swagger
  //TODO: authorize
  //TODO: hash password
  //TODO: loggggggggg

  @Override
  public SuccessfulResponse createOrderDetail(CreateOrderDetailRequest request) {
    //Validate
    

    //Check null
    
    Optional<Order> optionalOrder = this.orderRepository.findById(request.getOrderId());
    Order order = null;
    
    if (optionalOrder.isEmpty()) {
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Kh??ng t???n t???i gi??? h??ng n??o v???i orderId = " + request.getOrderId());
    }
    else {
      order = optionalOrder.get();
    }
    
    
    Optional<Product> optionalProduct = this.productRepository.findById(request.getProductId());
    Product product = null;
    
    if (optionalProduct.isEmpty()) {
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Kh??ng t???n t???i s???n ph???m n??o v???i productId = " + request.getProductId());
    }
    else {
      product = optionalProduct.get();
    }
    
    
    OrderDetail orderDetail = new OrderDetail();
    
    orderDetail.setPrice(request.getPrice());
    orderDetail.setDiscount(request.getDiscount());
    orderDetail.setQuantity(request.getQuantity());
    orderDetail.setOrder(order);
    orderDetail.setProduct(product);

    //Save to database
    this.orderDetailRepository.save(orderDetail);

    //Return
    OrderDetailResponse orderDetailDTO = new OrderDetailResponse(orderDetail);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(orderDetailDTO);
    response.addMessage("T???o m???t h??ng th??nh c??ng");

    return response;
  }

  @Override
  public GetOrderDetailResponse getOrderDetailById(Integer id) {
    if (!this.orderDetailRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Kh??ng t??m th???y m???t h??ng n??o v???i id l?? " + id);
    }

    OrderDetail orderDetail = this.orderDetailRepository.findById(id).get();
    OrderDetailResponse orderDetailDTO = new OrderDetailResponse(orderDetail);
    GetOrderDetailResponse response = new GetOrderDetailResponse(orderDetailDTO);

    response.addMessage("L???y d??? li???u th??nh c??ng");

    return response;
  }

  @Override
  public ListOrderDetailResponse searchOrderDetails(Map<String, String> queries) {
    List<OrderDetailResponse> listOrderDetailResponses = this.orderDetailRepository.searchOrderDetail(queries)
          .stream().map(orderDetail -> new OrderDetailResponse(orderDetail)).toList();
    
    ListOrderDetailResponse response = new ListOrderDetailResponse(listOrderDetailResponses);
    response.addMessage("L???y d??? li???u th??nh c??ng");

    return response;
  }

  @Override
  public SuccessfulResponse updateOrderDetail(UpdateOrderDetailRequest request) {
    //Check record exists
    if (!this.orderDetailRepository.existsById(request.getOrderDetailId())) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Kh??ng t??m th???y m???t h??ng n??o v???i id l?? " + request.getOrderDetailId());
    }

    //Read data from request
    OrderDetail orderDetail = this.orderDetailRepository.findById(request.getOrderDetailId()).get();
    
    Optional<Order> optionalOrder = this.orderRepository.findById(request.getOrderId());
    Order order = null;
    
    if (optionalOrder.isEmpty()) { 
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Kh??ng t???n t???i m???t h??ng n??o v???i orderId = " + request.getOrderId());
    }
    else {
      order = optionalOrder.get();
    }
    
    
    Optional<Product> optionalProduct = this.productRepository.findById(request.getProductId());
    Product product = null;
    
    if (optionalProduct.isEmpty()) { 
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Kh??ng t???n t???i m???t h??ng n??o v???i productId = " + request.getProductId());
    }
    else {
      product = optionalProduct.get();
    }
    
    
    
    orderDetail.setPrice(request.getPrice());
    orderDetail.setDiscount(request.getDiscount());
    orderDetail.setQuantity(request.getQuantity());
    orderDetail.setOrder(order);
    orderDetail.setProduct(product);

    //Validate unique
    

    //Update last changed time
    orderDetail.setLastUpdatedAt(new Date());

    //Store
    this.orderDetailRepository.save(orderDetail);

    //Return
    OrderDetailResponse orderDetailDTO = new OrderDetailResponse(orderDetail);
    SuccessfulResponse response = new SuccessfulResponse();

    response.setData(orderDetailDTO);
    response.addMessage("C???p nh???t m???t h??ng th??nh c??ng");

    return response;
  }
  

  @Override
  public SuccessfulResponse deleteOrderDetail(Integer id) {
    if (!this.orderDetailRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Kh??ng t??m th???y m???t h??ng n??o v???i id l?? " + id);
    }

    OrderDetail orderDetail = this.orderDetailRepository.findById(id).get();
    orderDetail.setDeletedAt(new Date());
    
    this.orderDetailRepository.save(orderDetail);

    SuccessfulResponse response = new SuccessfulResponse();
    response.addMessage("X??a m???t h??ng th??nh c??ng");

    return response;
  }
  
}
  