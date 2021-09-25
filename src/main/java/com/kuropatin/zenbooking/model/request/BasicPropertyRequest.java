package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.validation.ShortInRange;
import com.kuropatin.zenbooking.validation.ValueOfEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
abstract class BasicPropertyRequest {

    @NotBlank(message = "Enter property type")
    @ValueOfEnum(enumClass = PropertyType.class, message = "Allowed types are HOUSE, APARTMENT, ROOM, NOT_SPECIFIED")
    protected String type;

    @ShortInRange(min = 1, max = 10, message = "Enter number of guests, should be between 1 and 10")
    protected String guests;

    @ShortInRange(min = 1, max = 10, message = "Enter rooms of guests, should be between 1 and 10")
    protected String rooms;

    @ShortInRange(min = 1, max = 10, message = "Enter number of beds, should be between 1 and 10")
    protected String beds;

    @NotBlank(message = "Enter if property has kitchen")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    protected String hasKitchen;

    @NotBlank(message = "Enter if property has washer")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    protected String hasWasher;

    @NotBlank(message = "Enter if property has TV")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    protected String hasTv;

    @NotBlank(message = "Enter if property has internet")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    protected String hasInternet;

    @NotBlank(message = "Enter if pets allowed")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    protected String isPetsAllowed;
}