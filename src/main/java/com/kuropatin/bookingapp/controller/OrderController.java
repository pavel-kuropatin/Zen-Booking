package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.Order;
import com.kuropatin.bookingapp.model.dto.OrderDto;
import com.kuropatin.bookingapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/{userId}/booking")
@RequiredArgsConstructor
public class OrderController {

    public final OrderService service;

    @PostMapping("/{propertyId}/order")
    public ResponseEntity<Order> create(@PathVariable final Long userId, @PathVariable final Long propertyId, @RequestBody final OrderDto orderDto) {
        Order orderToSave = service.createOrder(userId, propertyId, orderDto);
        return new ResponseEntity<>(orderToSave, HttpStatus.CREATED);
    }
}