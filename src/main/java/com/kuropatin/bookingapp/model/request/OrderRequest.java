package com.kuropatin.bookingapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequest {

    @FutureOrPresent(message = "Date should be future or present")
    private LocalDate startDate;

    @FutureOrPresent(message = "Date should be future or present")
    private LocalDate endDate;
}