package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.response.OrderResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Api(value = "Order Controller", tags = "User Orders", description = "Actions for user orders")
public class OrderController {

    private final OrderService service;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Get list of all active orders of logged user")
    @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true)
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllActive() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToListOrderResponse(service.getActiveOrders(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get list of all cancelled and declined orders of logged user")
    @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true)
    @GetMapping("/cancelled")
    public ResponseEntity<List<OrderResponse>> getAllCancelled() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToListOrderResponse(service.getCancelledOrders(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get list of all finished orders of logged user")
    @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true)
    @GetMapping("/finished")
    public ResponseEntity<List<OrderResponse>> getAllFinished() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToListOrderResponse(service.getFinishedOrders(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get order with id {orderId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true),
            @ApiImplicitParam(name = "orderId", dataTypeClass = String.class, paramType = "path", value = "orderId", required = true)
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getById(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToNewOrderResponse(service.getOrderById(orderId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Cancel order with id {orderId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true),
            @ApiImplicitParam(name = "orderId", dataTypeClass = String.class, paramType = "path", value = "orderId", required = true)
    })
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancel(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.cancelOrder(orderId, userId), HttpStatus.OK);
    }
}