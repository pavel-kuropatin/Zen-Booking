package com.kuropatin.bookingapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AmountRequest {

    @Positive(message = "Amount should be positive number")
    @Max(value = 1000, message = "Max value is 1000")
    private int amount = 0;
}