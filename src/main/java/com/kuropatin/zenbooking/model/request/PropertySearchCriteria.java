package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.validation.DatePresentOrFuture;
import com.kuropatin.zenbooking.validation.IntegerInRange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class PropertySearchCriteria extends BasicPropertyRequest {

    @NotNull(message = "Enter address (can be blank)")
    @Size(max = 500, message = "Address should be between 500 characters or less")
    private String address;

    @IntegerInRange(min = 1, max = 1000, message = "Minimum price should be between 1 and 1000")
    private String priceMin;

    @IntegerInRange(min = 1, max = 1000, message = "Maximum price should be between 1 and 1000")
    private String priceMax;

    @NotBlank(message = "Enter start date")
    @DatePresentOrFuture
    private String startDate;

    @NotBlank(message = "Enter end date")
    @DatePresentOrFuture
    private String endDate;
}