package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.*;
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

    public List<Order> getActiveOrders(Long userId) {
        return orderRepository.findAllActiveOrdersOfUser(userId);
    }

    public List<Order> getCancelledOrders(Long userId) {
        return orderRepository.findAllCancelledOrdersOfUser(userId);
    }

    public List<Order> getFinishedOrders(Long userId) {
        return orderRepository.findAllFinishedOrdersOfUser(userId);
    }

    public Order getOrderById(Long orderId, Long userId) {
        if(orderRepository.existsByIdAndUserIdAndIsFinishedFalse(orderId, userId)) {
            return orderRepository.findOrderByIdAndUserId(orderId, userId);
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Transactional(rollbackFor = {
            UserNotFoundException.class,
            PropertyNotFoundException.class,
            InsufficientMoneyAmountException.class
    })
    public Order createOrder(Long clientId, Long propertyId, OrderRequest orderRequest) {
        if(orderRepository.canPropertyBeOrdered(LocalDate.parse(orderRequest.getStartDate()), LocalDate.parse(orderRequest.getEndDate()))) {
            User client = userService.getUserById(clientId);
            Property propertyToOrder = propertyService.getPropertyById(propertyId, clientId);
            Order order = new Order();
            order.setStartDate(LocalDate.parse(orderRequest.getStartDate()));
            order.setEndDate(LocalDate.parse(orderRequest.getEndDate()));
            order.setTotalPrice(calculateTotalPrice(propertyToOrder.getPrice(), order.getStartDate(), order.getEndDate()));
            userService.pay(client, order.getTotalPrice());
            order.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            order.setUpdated(order.getCreated());
            order.setUser(client);
            order.setProperty(propertyToOrder);
            client.setOrders(Collections.singleton(order));
            propertyToOrder.setOrder(order);
            return orderRepository.save(order);
        } else {
            throw new PropertyCannotBeOrderedException(propertyId);
        }
    }

    @Transactional(rollbackFor = {
            OrderNotFoundException.class,
            UserNotFoundException.class
    })
    public String cancelOrder(Long orderId, Long userId) {
        if(orderRepository.existsByIdAndUserIdAndIsFinishedFalse(orderId, userId)) {
            Order orderToCancel = getOrderById(orderId, userId);
            if(!orderToCancel.isAccepted() && !orderToCancel.isCancelled()) {
                userService.transferMoney(orderToCancel.getUser(), orderToCancel.getTotalPrice());
                orderRepository.cancelOrder(orderId, Timestamp.valueOf(LocalDateTime.now()));
                return MessageFormat.format("Order with id: {0} successfully cancelled", orderId);
            } else {
                throw new OrderCannotBeCancelledException(orderId);
            }
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    public List<Order> getAllOrderRequestsOfUser(Long userId) {
        return orderRepository.findAllOrderRequests(userId);
    }

    public Order getOrderRequestByIdAndUserId(Long orderId, Long userId) {
        if (orderRepository.existsByIdAndHostId(orderId, userId)) {
            return orderRepository.findOrderRequestByIdAndHostId(orderId, userId);
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Transactional(rollbackFor = {
            OrderNotFoundException.class,
            UserNotFoundException.class
    })
    public String acceptOrder(Long orderId, Long hostId) {
        if(orderRepository.existsByIdAndHostId(orderId, hostId)) {
            Order orderToAccept = orderRepository.findOrderById(orderId);
            if(!orderToAccept.isAccepted() && !orderToAccept.isCancelled()) {
                User host = userService.getUserById(hostId);
                userService.transferMoney(host, orderToAccept.getTotalPrice());
                orderRepository.acceptOrder(orderId, Timestamp.valueOf(LocalDateTime.now()));
                return MessageFormat.format("Order with id: {0} successfully accepted", orderId);
            } else {
                throw new OrderCannotBeAcceptedException(orderId);
            }
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Transactional(rollbackFor = {
            OrderNotFoundException.class,
            UserNotFoundException.class
    })
    public String declineOrder(Long orderId, Long hostId) {
        if (orderRepository.existsByIdAndHostId(orderId, hostId)) {
            Order orderToDecline = orderRepository.findOrderById(orderId);
            if (!orderToDecline.isAccepted() && !orderToDecline.isCancelled() && !orderToDecline.isFinished()) {
                userService.transferMoney(orderToDecline.getUser(), orderToDecline.getTotalPrice());
                orderRepository.declineOrder(orderId, Timestamp.valueOf(LocalDateTime.now()));
                return MessageFormat.format("Order with id: {0} successfully declined", orderId);
            } else {
                throw new OrderCannotBeDeclinedException(orderId);
            }
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    private int calculateTotalPrice(int price, LocalDate startDate, LocalDate endDate) {
        int days = 1 + Period.between(startDate, endDate).getDays();
        return price * days;
    }
}