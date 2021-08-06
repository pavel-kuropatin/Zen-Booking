package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertySearchCriteria {

    private PropertyType type = PropertyType.TYPE_NOT_SPECIFIED;

    @Size(max = 500, message = "Address should be between 500 characters or less")
    private String address;

    @Positive(message = "Min price should be a positive number")
    @Max(value = 1000, message = "Min price is 1000")
    private int priceMin = 0;

    @Positive(message = "Max price should be a positive number")
    @Max(value = 1000, message = "Max price is 1000")
    private int priceMax = 1000;

    @Positive(message = "Number of guests should be a positive number")
    @Max(value = 10, message = "Max number of guests is 10")
    private short guests = 1;

    @Positive(message = "Number of rooms should be a positive number")
    @Max(value = 10, message = "Max number of rooms value is 10")
    private short rooms = 1;

    @Positive(message = "Number of beds should be a positive number")
    @Max(value = 10, message = "Max number of beds is 10")
    private short beds = 10;

    private boolean hasKitchen = false;
    private boolean hasWasher = false;
    private boolean hasTv = false;
    private boolean hasInternet = false;
    private boolean isPetsAllowed = false;

    @FutureOrPresent(message = "Date should be future or present")
    private LocalDate startDate;

    @FutureOrPresent(message = "Date should be future or present")
    private LocalDate endDate;
}