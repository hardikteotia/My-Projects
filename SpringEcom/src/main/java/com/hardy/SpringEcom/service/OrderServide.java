package com.hardy.SpringEcom.service;

import com.hardy.SpringEcom.model.Order;
import com.hardy.SpringEcom.model.OrderItem;
import com.hardy.SpringEcom.model.Product;
import com.hardy.SpringEcom.model.dto.OrderItemRequest;
import com.hardy.SpringEcom.model.dto.OrderItemResponse;
import com.hardy.SpringEcom.model.dto.OrderRequest;
import com.hardy.SpringEcom.model.dto.OrderResponse;
import com.hardy.SpringEcom.repo.OrderRepo;
import com.hardy.SpringEcom.repo.ProductRepo;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServide {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;



    public OrderResponse placeOrder(OrderRequest request) {
        Order order = new Order();
        String orderId = "ORD"+UUID.randomUUID().toString().substring(0,8).toUpperCase();
        order.setOrderId(orderId);
        order.setCustomerName(request.name());
        order.setEmail(request.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequest itemReq:request.items()){
            Product product = productRepo.findById(itemReq.productId()).orElseThrow(()->new RuntimeException("Product Not Found"));
            product.setStockQuantity(product.getStockQuantity() - itemReq.quantity());

            productRepo.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.quantity())
                    .price(product.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())))
                    .order(order)
                    .build();

            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepo.save(order);

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for(OrderItem orderItem:order.getOrderItems()){
            OrderItemResponse orderItemResponse =new OrderItemResponse(
                    orderItem.getProduct().getName(),
                    orderItem.getQuantity(),
                    orderItem.getPrice()
            );
        }


        OrderResponse orderResponse = new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                orderItemResponses);
        return orderResponse;
    }


    public List<OrderResponse> getAllOrderResponses() {

        List<Order>  ordersList = orderRepo.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order orders: ordersList){

            List<OrderItemResponse> orderItemResponses = new ArrayList<>();

            for(OrderItem orderItem:orders.getOrderItems()){
                OrderItemResponse orderItemResponse =new OrderItemResponse(
                  orderItem.getProduct().getName(),
                        orderItem.getQuantity(),
                        orderItem.getPrice()
                );
                orderItemResponses.add(orderItemResponse);
            }

            OrderResponse orderResponse = new OrderResponse(
                    orders.getOrderId(),
                    orders.getCustomerName(),
                    orders.getEmail(),
                    orders.getStatus(),
                    orders.getOrderDate(),
                    orderItemResponses
            );
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }
}
