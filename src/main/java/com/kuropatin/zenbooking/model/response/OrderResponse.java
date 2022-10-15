package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.model.OrderStatus;
import com.kuropatin.zenbooking.util.ToStringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Response {

    private Long id;
    private Integer totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private OrderStatus status;
    private Long clientId;
    private Long hostId;
    private Long propertyId;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}