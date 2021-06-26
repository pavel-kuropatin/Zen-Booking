package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.OrderRequest;
import com.kuropatin.bookingapp.model.response.OrderResponse;
import com.kuropatin.bookingapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        return new ResponseEntity<>(OrderResponse.transformToListOrderResponse(service.getAllOrders()), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getById(@PathVariable final Long orderId) {
        return new ResponseEntity<>(OrderResponse.transformToNewOrderResponse(service.getOrderById(orderId)), HttpStatus.OK);
    }

//    TODO: move to /booking/{propertyId}/
//    @PostMapping("/{propertyId}/order")
//    public ResponseEntity<Order> create(@PathVariable final Long propertyId, @RequestBody final OrderRequest orderRequest) {
//        long userId = authenticationUtils.getId();
//        Order orderToSave = service.createOrder(userId, propertyId, orderRequest);
//        return new ResponseEntity<>(orderToSave, HttpStatus.CREATED);
//    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> update(@PathVariable final Long orderId, @RequestBody final OrderRequest orderRequest) {
        return new ResponseEntity<>(OrderResponse.transformToNewOrderResponse(service.updateOrder(orderId, orderRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancel(@PathVariable final Long orderId) {
        return new ResponseEntity<>(service.cancelOrder(orderId), HttpStatus.OK);
    }
}