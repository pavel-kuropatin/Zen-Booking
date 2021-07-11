package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.OrderRequest;
import com.kuropatin.bookingapp.model.response.OrderResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
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
    private final AuthenticationUtils authenticationUtils;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToListOrderResponse(service.getAllOrders(userId)), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getById(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToNewOrderResponse(service.getOrderById(orderId, userId)), HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> update(@PathVariable final Long orderId, @RequestBody final OrderRequest orderRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToNewOrderResponse(service.updateOrder(orderId, userId, orderRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> delete(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.cancelOrder(orderId, userId), HttpStatus.OK);
    }
}