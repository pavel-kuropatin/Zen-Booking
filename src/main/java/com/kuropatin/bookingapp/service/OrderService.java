package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.OrderNotFoundException;
import com.kuropatin.bookingapp.model.Order;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.request.OrderRequest;
import com.kuropatin.bookingapp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final PropertyService propertyService;

    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }

    public Order getOrderById(Long orderId) {
        if(orderRepository.existsById(orderId)) {
            return orderRepository.findOrderById(orderId);
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    //@Transactional(rollbackForClassName = {"InsufficientMoneyAmountException"})
    public Order createOrder(Long userId, Long propertyId, OrderRequest orderRequest) {
        User user = userService.getUserById(userId);
        Property property = propertyService.getPropertyById(propertyId);
        Order order = new Order();
        order.setStartDate(orderRequest.getStartDate());
        order.setEndDate(orderRequest.getEndDate());
        order.setTotalPrice(calculateTotalPrice(property.getPrice(), order.getStartDate(), order.getEndDate()));
        userService.pay(user, order.getTotalPrice());
        order.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        order.setUpdated(order.getCreated());
        order.setUser(user);
        order.setProperty(property);
        user.setOrders(Collections.singleton(order));
        property.setOrder(order);
        return orderRepository.save(order);
    }

    //@Transactional(rollbackForClassName = {"InsufficientMoneyAmountException"})
    public Order updateOrder(Long orderId, OrderRequest orderRequest) {
        Order orderToUpdate = getOrderById(orderId);
        userService.payBack(orderToUpdate.getUser(), orderToUpdate.getTotalPrice());
        orderToUpdate.setStartDate(orderRequest.getStartDate());
        orderToUpdate.setEndDate(orderRequest.getEndDate());
        orderToUpdate.setTotalPrice(calculateTotalPrice(orderToUpdate.getProperty().getPrice(), orderToUpdate.getStartDate(), orderToUpdate.getEndDate()));
        userService.pay(orderToUpdate.getUser(), orderToUpdate.getTotalPrice());
        orderToUpdate.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return orderRepository.save(orderToUpdate);
    }

    //@Transactional(rollbackForClassName = {"InsufficientMoneyAmountException"})
    public String cancelOrder(Long orderId) {
        if(orderRepository.existsById(orderId)) {
            //TODO: add money transfer back
            orderRepository.cancelOrder(orderId);
            return MessageFormat.format("Order with id: {0} successfully cancelled", orderId);
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    public Order acceptOrder(Long orderId) {
        if(orderRepository.existsById(orderId)) {
            //TODO: add money transfer to host
            return orderRepository.acceptOrder(orderId);
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    private int calculateTotalPrice(int price, LocalDate startDate, LocalDate endDate) {
        int days = 1 + Period.between(startDate, endDate).getDays();
        return price * days;
    }
}