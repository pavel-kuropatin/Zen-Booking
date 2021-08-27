package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.response.OrderResponse;
import com.kuropatin.bookingapp.model.response.SuccessfulResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Api(value = "Order Controller", tags = "User Orders", description = "Actions for user orders")
public class OrderController {

    private final OrderService service;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Get list of all active orders of logged user")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllActive() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.transformToListOrderResponse(service.getActiveOrders(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get list of all finished orders of logged user")
    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>> getAllFinished() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.transformToListOrderResponse(service.getOrderHistory(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get order with id {orderId} of logged user")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getById(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.transformToNewOrderResponse(service.getOrderById(orderId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Cancel order with id {orderId} of logged user")
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<SuccessfulResponse> cancel(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.cancelOrder(orderId, userId), HttpStatus.OK);
    }
}