package com.hardy.SpringEcom.controller;

import com.hardy.SpringEcom.model.dto.OrderRequest;
import com.hardy.SpringEcom.model.dto.OrderResponse;
import com.hardy.SpringEcom.service.OrderServide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderServide orderServide;

    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){

        OrderResponse orderResponse = orderServide.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        List<OrderResponse> responses = orderServide.getAllOrderResponses();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
