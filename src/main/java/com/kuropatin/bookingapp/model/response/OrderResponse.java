package com.kuropatin.bookingapp.model.response;

import com.kuropatin.bookingapp.model.Order;
import com.kuropatin.bookingapp.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponse {

    private long id;
    private int totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAccepted;
    private boolean isCancelled;
    private boolean isFinished;
    private long clientId;
    private long hostId;
    private long propertyId;
    private String status; //TODO: add status

    public static OrderResponse transformToNewOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        transformToOrderResponse(order, orderResponse);
        return orderResponse;
    }

    public static List<OrderResponse> transformToListOrderResponse(List<Order> orders) {
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for(Order order : orders) {
            orderResponseList.add(transformToNewOrderResponse(order));
        }
        return orderResponseList;
    }

    private static OrderResponse transformToOrderResponse(Order order, OrderResponse orderResponse) {
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
            orderResponse.setStatus(OrderStatus.ACTIVE_NOT_ACCEPTED);
        } else if(orderResponse.isAccepted() && !orderResponse.isCancelled() && !orderResponse.isFinished()) {
            orderResponse.setStatus(OrderStatus.ACTIVE_ACCEPTED);
        } else if(!orderResponse.isAccepted() && orderResponse.isCancelled() && orderResponse.isFinished()) {
            orderResponse.setStatus(OrderStatus.CANCELLED);
        } else if(!orderResponse.isAccepted() && !orderResponse.isCancelled() && orderResponse.isFinished()) {
            orderResponse.setStatus(OrderStatus.DECLINED);
        } else if(orderResponse.isAccepted() && !orderResponse.isCancelled() && orderResponse.isFinished()) {
            orderResponse.setStatus(OrderStatus.FINISHED);
        } else {
            orderResponse.setStatus(OrderStatus.STATUS_UNKNOWN);
        }
        return orderResponse;
    }
}