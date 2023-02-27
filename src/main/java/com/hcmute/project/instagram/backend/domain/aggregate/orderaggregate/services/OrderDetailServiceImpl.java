package com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services;

import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.dto.orderdetail.*;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.Order;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.entities.OrderDetail;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.repositories.OrderDetailRepository;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.repositories.OrderRepository;
import com.hcmute.project.instagram.backend.domain.aggregate.orderaggregate.services.interfaces.OrderDetailService;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.entities.Product;
import com.hcmute.project.instagram.backend.domain.aggregate.productaggregate.repositories.ProductRepository;
import com.hcmute.project.instagram.backend.domain.base.StorageRepository;
import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;
import com.hcmute.project.instagram.backend.domain.exception.ServiceExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

  @Autowired
  private OrderDetailRepository orderDetailRepository;
  
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private OrderRepository orderRepository;
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

  @Override
  public SuccessfulResponse createOrderDetail(CreateOrderDetailRequest request) {
    //Validate
    

    //Check null
    
    Optional<Order> optionalOrder = this.orderRepository.findById(request.getOrderId());
    Order order = null;
    
    if (optionalOrder.isEmpty()) {
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Không tồn tại giỏ hàng nào với orderId = " + request.getOrderId());
    }
    else {
      order = optionalOrder.get();
    }
    
    
    Optional<Product> optionalProduct = this.productRepository.findById(request.getProductId());
    Product product = null;
    
    if (optionalProduct.isEmpty()) {
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Không tồn tại sản phẩm nào với productId = " + request.getProductId());
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
    response.addMessage("Tạo mặt hàng thành công");

    return response;
  }

  @Override
  public GetOrderDetailResponse getOrderDetailById(Integer id) {
    if (!this.orderDetailRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy mặt hàng nào với id là " + id);
    }

    OrderDetail orderDetail = this.orderDetailRepository.findById(id).get();
    OrderDetailResponse orderDetailDTO = new OrderDetailResponse(orderDetail);
    GetOrderDetailResponse response = new GetOrderDetailResponse(orderDetailDTO);

    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public ListOrderDetailResponse listOrderDetails() {
    List<OrderDetailResponse> listOrderDetailResponses = this.orderDetailRepository.findAll()
          .stream().map(orderDetail -> new OrderDetailResponse(orderDetail)).toList();
    
    ListOrderDetailResponse response = new ListOrderDetailResponse(listOrderDetailResponses);
    response.addMessage("Lấy dữ liệu thành công");

    return response;
  }

  @Override
  public SuccessfulResponse updateOrderDetail(UpdateOrderDetailRequest request) {
    //Check record exists
    if (!this.orderDetailRepository.existsById(request.getOrderDetailId())) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy mặt hàng nào với id là " + request.getOrderDetailId());
    }

    //Read data from request
    OrderDetail orderDetail = this.orderDetailRepository.findById(request.getOrderDetailId()).get();
    
    Optional<Order> optionalOrder = this.orderRepository.findById(request.getOrderId());
    Order order = null;
    
    if (optionalOrder.isEmpty()) { 
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Không tồn tại mặt hàng nào với orderId = " + request.getOrderId());
    }
    else {
      order = optionalOrder.get();
    }
    
    
    Optional<Product> optionalProduct = this.productRepository.findById(request.getProductId());
    Product product = null;
    
    if (optionalProduct.isEmpty()) { 
      throw ServiceExceptionFactory.badRequest()
        .addMessage("Không tồn tại mặt hàng nào với productId = " + request.getProductId());
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
    response.addMessage("Cập nhật mặt hàng thành công");

    return response;
  }
  

  @Override
  public SuccessfulResponse deleteOrderDetail(Integer id) {
    if (!this.orderDetailRepository.existsById(id)) {
      throw ServiceExceptionFactory.notFound()
        .addMessage("Không tìm thấy mặt hàng nào với id là " + id);
    }

    OrderDetail orderDetail = this.orderDetailRepository.findById(id).get();
    orderDetail.setDeletedAt(new Date());
    
    this.orderDetailRepository.save(orderDetail);

    SuccessfulResponse response = new SuccessfulResponse();
    response.addMessage("Xóa mặt hàng thành công");

    return response;
  }
}
  