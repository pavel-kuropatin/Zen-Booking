package com.kuropatin.zenbooking.model.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.LocalDate;

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
    private String status;

    @SneakyThrows
    @Override
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}