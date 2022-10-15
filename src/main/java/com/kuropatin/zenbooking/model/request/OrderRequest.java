package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.util.ToStringUtils;
import com.kuropatin.zenbooking.validation.DatePresentOrFuture;
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
public class OrderRequest implements Request {

    @DatePresentOrFuture
    private String startDate;

    @DatePresentOrFuture
    private String endDate;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}