package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.validation.DatePresentOrFuture;
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