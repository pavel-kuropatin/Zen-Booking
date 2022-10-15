package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.util.ToStringUtils;
import com.kuropatin.zenbooking.validation.IntegerInRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountRequest implements Request {

    @IntegerInRange(min = 1, max = 1000, message = "Enter amount to deposit, should be between 1 and 1000")
    private Integer amount;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}