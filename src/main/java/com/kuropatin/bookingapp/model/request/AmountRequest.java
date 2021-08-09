package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.validation.IntegerInRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AmountRequest {

    @IntegerInRange(min = 1, max = 1000, message = "Enter amount to deposit, should be between 1 and 1000")
    private String amount;
}