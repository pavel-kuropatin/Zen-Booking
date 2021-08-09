package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.model.PropertyType;
import com.kuropatin.bookingapp.validation.DatePresentOrFuture;
import com.kuropatin.bookingapp.validation.IntegerInRange;
import com.kuropatin.bookingapp.validation.ShortInRange;
import com.kuropatin.bookingapp.validation.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PropertySearchCriteria {

    @NotBlank(message = "Enter property type")
    @ValueOfEnum(enumClass = PropertyType.class, message = "Allowed types are HOUSE, APARTMENT, ROOM, NOT_SPECIFIED")
    private String type;

    @NotNull(message = "Enter address (can be blank)")
    @Size(max = 500, message = "Address should be between 500 characters or less")
    private String address;

    @IntegerInRange(min = 1, max = 1000, message = "Minimum price should be between 1 and 1000")
    private String priceMin;

    @IntegerInRange(min = 1, max = 1000, message = "Maximum price should be between 1 and 1000")
    private String priceMax;

    @ShortInRange(min = 1, max = 10, message = "Number of guests should be between 1 and 10")
    private String guests;

    @ShortInRange(min = 1, max = 10, message = "Number of rooms should be between 1 and 10")
    private String rooms;

    @ShortInRange(min = 1, max = 10, message = "Number of beds should be between 1 and 10")
    private String beds;

    @NotBlank(message = "Enter if property has kitchen")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    private String hasKitchen;

    @NotBlank(message = "Enter if property has washer")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    private String hasWasher;

    @NotBlank(message = "Enter if property has TV")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    private String hasTv;

    @NotBlank(message = "Enter if property has internet")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    private String hasInternet;

    @NotBlank(message = "Enter if pets allowed")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    private String isPetsAllowed;

    @NotBlank(message = "Enter start date")
    @DatePresentOrFuture
    private String startDate;

    @NotBlank(message = "Enter end date")
    @DatePresentOrFuture
    private String endDate;
}