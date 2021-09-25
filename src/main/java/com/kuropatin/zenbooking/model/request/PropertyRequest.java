package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.validation.IntegerInRange;
import com.kuropatin.zenbooking.validation.ShortInRange;
import com.kuropatin.zenbooking.validation.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PropertyRequest {

    @NotBlank(message = "Enter property type")
    @ValueOfEnum(enumClass = PropertyType.class, message = "Allowed types are HOUSE, APARTMENT, ROOM, NOT_SPECIFIED")
    private String type;

    @NotBlank(message = "Enter property name")
    @Size(min = 2, max = 100, message = "Property name should be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Enter property description")
    @Size(min = 2, max = 500, message = "Property description should be between 2 and 500 characters")
    private String description;

    @NotBlank(message = "Enter address")
    @Size(max = 500, message = "Address should be between 500 characters or lower")
    private String address;

    @IntegerInRange(min = 1, max = 1000, message = "Enter price, should be between 1 and 1000")
    private String price;

    @ShortInRange(min = 1, max = 10, message = "Enter number of guests, should be between 1 and 10")
    private String guests;

    @ShortInRange(min = 1, max = 10, message = "Enter rooms of guests, should be between 1 and 10")
    private String rooms;

    @ShortInRange(min = 1, max = 10, message = "Enter number of beds, should be between 1 and 10")
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

    @NotBlank(message = "Enter if property available for booking")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    private String isAvailable;
}