package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.validation.DatePresentOrFuture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderRequest {

    @DatePresentOrFuture
    private String startDate;

    @DatePresentOrFuture
    private String endDate;
}