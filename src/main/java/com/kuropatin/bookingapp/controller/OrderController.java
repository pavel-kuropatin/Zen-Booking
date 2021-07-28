package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.OrderRequest;
import com.kuropatin.bookingapp.model.response.OrderResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.OrderService;
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
public class OrderController {

    private final OrderService service;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Get list of all orders of logged user")
    @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToListOrderResponse(service.getAllOrders(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get order with id {orderId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "orderId", dataTypeClass = Integer.class, paramType = "path", value = "orderId", required = true, defaultValue = "1")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getById(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToNewOrderResponse(service.getOrderById(orderId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Update order with id {orderId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "orderId", dataTypeClass = Integer.class, paramType = "path", value = "orderId", required = true, defaultValue = "1")
    })
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> update(@PathVariable final Long orderId, @RequestBody final OrderRequest orderRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToNewOrderResponse(service.updateOrder(orderId, userId, orderRequest)), HttpStatus.OK);
    }

    @ApiOperation(value = "Cancel order with id {orderId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "orderId", dataTypeClass = Integer.class, paramType = "path", value = "orderId", required = true, defaultValue = "1")
    })
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancel(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.cancelOrder(orderId, userId), HttpStatus.OK);
    }
}