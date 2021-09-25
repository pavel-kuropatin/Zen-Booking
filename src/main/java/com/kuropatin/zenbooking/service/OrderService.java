package com.kuropatin.zenbooking.service;

import com.kuropatin.zenbooking.exception.InsufficientMoneyAmountException;
import com.kuropatin.zenbooking.exception.MoneyAmountExceededException;
import com.kuropatin.zenbooking.exception.OrderCannotBeAcceptedException;
import com.kuropatin.zenbooking.exception.OrderCannotBeCancelledException;
import com.kuropatin.zenbooking.exception.OrderCannotBeDeclinedException;
import com.kuropatin.zenbooking.exception.OrderNotFoundException;
import com.kuropatin.zenbooking.exception.PropertyCannotBeOrderedException;
import com.kuropatin.zenbooking.exception.PropertyNotFoundException;
import com.kuropatin.zenbooking.exception.UserNotFoundException;
import com.kuropatin.zenbooking.model.Order;
import com.kuropatin.zenbooking.model.OrderStatus;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.User;
import com.kuropatin.zenbooking.model.request.OrderRequest;
import com.kuropatin.zenbooking.model.response.OrderResponse;
import com.kuropatin.zenbooking.model.response.SuccessfulResponse;
import com.kuropatin.zenbooking.repository.OrderRepository;
import com.kuropatin.zenbooking.util.ApplicationTimestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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

    public List<Order> getOrderHistory(Long userId) {
        return orderRepository.findAllFinishedOrdersOfUser(userId);
    }

    public List<Order> getOrdersToAddReview(Long userId) {
        return orderRepository.findOrdersToAddReview(userId);
    }

    public Order getOrderById(Long orderId, Long userId) {
        if(orderRepository.existsByIdAndUserId(orderId, userId)) {
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
        if(propertyService.canPropertyBeOrdered(LocalDate.parse(orderRequest.getStartDate()), LocalDate.parse(orderRequest.getEndDate()), propertyId)) {
            User client = userService.getUserById(clientId);
            Property propertyToOrder = propertyService.getPropertyById(propertyId, clientId);
            Order order = new Order();
            order.setStartDate(LocalDate.parse(orderRequest.getStartDate()));
            order.setEndDate(LocalDate.parse(orderRequest.getEndDate()));
            order.setTotalPrice(calculateTotalPrice(propertyToOrder.getPrice(), order.getStartDate(), order.getEndDate()));
            Timestamp timestamp = ApplicationTimestamp.getTimestampUTC();
            userService.pay(client, order.getTotalPrice(), timestamp);
            order.setCreated(timestamp);
            order.setUpdated(timestamp);
            order.setUser(client);
            order.setProperty(propertyToOrder);
            client.addOrder(order);
            propertyToOrder.setOrder(order);
            return orderRepository.save(order);
        } else {
            throw new PropertyCannotBeOrderedException(propertyId);
        }
    }

    @Transactional(rollbackFor = {
            OrderNotFoundException.class,
            UserNotFoundException.class,
            MoneyAmountExceededException.class
    })
    public SuccessfulResponse cancelOrder(Long orderId, Long userId) {
        if(orderRepository.existsByIdAndUserId(orderId, userId)) {
            Order orderToCancel = getOrderById(orderId, userId);
            if(!orderToCancel.isAccepted() && !orderToCancel.isCancelled()) {
                Timestamp timestamp = ApplicationTimestamp.getTimestampUTC();
                userService.transferMoney(orderToCancel.getUser(), orderToCancel.getTotalPrice(), timestamp);
                orderRepository.cancelOrder(orderId, timestamp);
                return new SuccessfulResponse(timestamp, MessageFormat.format("Order with id: {0} successfully cancelled", orderId));
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
            UserNotFoundException.class,
            MoneyAmountExceededException.class
    })
    public SuccessfulResponse acceptOrder(Long orderId, Long hostId) {
        if(orderRepository.existsByIdAndHostId(orderId, hostId)) {
            Order orderToAccept = orderRepository.findOrderById(orderId);
            if(!orderToAccept.isAccepted() && !orderToAccept.isCancelled()) {
                User host = userService.getUserById(hostId);
                Timestamp timestamp = ApplicationTimestamp.getTimestampUTC();
                userService.transferMoney(host, orderToAccept.getTotalPrice(), timestamp);
                orderRepository.acceptOrder(orderId, timestamp);
                return new SuccessfulResponse(timestamp, MessageFormat.format("Order with id: {0} successfully accepted", orderId));
            } else {
                throw new OrderCannotBeAcceptedException(orderId);
            }
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Transactional(rollbackFor = {
            OrderNotFoundException.class,
            UserNotFoundException.class,
            MoneyAmountExceededException.class
    })
    public SuccessfulResponse declineOrder(Long orderId, Long hostId) {
        if (orderRepository.existsByIdAndHostId(orderId, hostId)) {
            Order orderToDecline = orderRepository.findOrderById(orderId);
            if (!orderToDecline.isAccepted() && !orderToDecline.isCancelled() && !orderToDecline.isFinished()) {
                Timestamp timestamp = ApplicationTimestamp.getTimestampUTC();
                userService.transferMoney(orderToDecline.getUser(), orderToDecline.getTotalPrice(), timestamp);
                orderRepository.declineOrder(orderId, timestamp);
                return new SuccessfulResponse(timestamp, MessageFormat.format("Order with id: {0} successfully declined", orderId));
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

    public OrderResponse transformToNewOrderResponse(Order order) {
        return transformToOrderResponse(order, new OrderResponse());
    }

    public List<OrderResponse> transformToListOrderResponse(List<Order> orders) {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for(Order order : orders) {
            orderResponseList.add(transformToNewOrderResponse(order));
        }
        return orderResponseList;
    }

    private OrderResponse transformToOrderResponse(Order order, OrderResponse orderResponse) {
        orderResponse.setId(order.getId());
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setStartDate(order.getStartDate());
        orderResponse.setEndDate(order.getEndDate());
        orderResponse.setAccepted(order.isAccepted());
        orderResponse.setCancelled(order.isCancelled());
        orderResponse.setFinished(order.isFinished());
        orderResponse.setClientId(order.getUser().getId());
        orderResponse.setHostId(order.getProperty().getUser().getId());
        orderResponse.setPropertyId(order.getProperty().getId());
        if(!orderResponse.isAccepted() && !orderResponse.isCancelled() && !orderResponse.isFinished()) {
            orderResponse.setStatus(OrderStatus.ACTIVE_NOT_ACCEPTED.label);
        } else if(orderResponse.isAccepted() && !orderResponse.isCancelled() && !orderResponse.isFinished()) {
            orderResponse.setStatus(OrderStatus.ACTIVE_ACCEPTED.label);
        } else if(!orderResponse.isAccepted() && orderResponse.isCancelled() && orderResponse.isFinished()) {
            orderResponse.setStatus(OrderStatus.CANCELLED.label);
        } else if(!orderResponse.isAccepted() && !orderResponse.isCancelled() && orderResponse.isFinished()) {
            orderResponse.setStatus(OrderStatus.DECLINED.label);
        } else if(orderResponse.isAccepted() && !orderResponse.isCancelled() && orderResponse.isFinished()) {
            orderResponse.setStatus(OrderStatus.FINISHED.label);
        } else {
            orderResponse.setStatus(OrderStatus.STATUS_UNKNOWN.label);
        }
        return orderResponse;
    }

    public List<Order> getOrdersToAutoAccept() {
        return orderRepository.findOrdersToAutoAccept();
    }

    public List<Order> getOrdersToAutoFinish() {
        return orderRepository.findOrdersToAutoFinish();
    }

    public void autoFinishOrder(Long id) {
        if(orderRepository.existsById(id)) {
            orderRepository.finishOrder(id, ApplicationTimestamp.getTimestampUTC());
        } else {
            throw new OrderNotFoundException(MessageFormat.format("Order with id: {0} cannot be finished automatically", id));
        }
    }
}