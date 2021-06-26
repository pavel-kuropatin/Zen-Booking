package com.kuropatin.bookingapp.model.response;

import com.kuropatin.bookingapp.model.Order;
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

    private int totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isAcceptedByHost;
    private boolean isCancelled;
    private boolean isFinished;
    private long propertyId;

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

    public static OrderResponse transformToOrderResponse(Order order, OrderResponse orderResponse) {
        orderResponse.setTotalPrice(order.getTotalPrice());
        orderResponse.setStartDate(order.getStartDate());
        orderResponse.setEndDate(order.getEndDate());
        orderResponse.setAcceptedByHost(order.isAcceptedByHost());
        orderResponse.setCancelled(order.isCancelled());
        orderResponse.setFinished(order.isFinished());
        orderResponse.setPropertyId(order.getProperty().getId());
        return orderResponse;
    }
}