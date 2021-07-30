package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertySearchCriteria {

    private PropertyType type = PropertyType.TYPE_NOT_SPECIFIED;
    private String address;
    private int priceMin = 0;
    private int priceMax = 1000;
    private short guests = 1;
    private short rooms = 1;
    private short beds = 1;
    private boolean hasKitchen = false;
    private boolean hasWasher = false;
    private boolean hasTv = false;
    private boolean hasInternet = false;
    private boolean isPetsAllowed = false;
    private LocalDate startDate;
    private LocalDate endDate;
}