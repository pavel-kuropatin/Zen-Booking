package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.validation.DatePresentOrFuture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderRequest {

    @DatePresentOrFuture
    private String startDate;

    @DatePresentOrFuture
    private String endDate;
}