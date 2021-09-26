package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.validation.DatePresentOrFuture;
import com.kuropatin.zenbooking.validation.EmptyOrIntegerInRange;
import com.kuropatin.zenbooking.validation.ValueOfEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class PropertySearchCriteria {

    @NotBlank(message = "Enter property type")
    @ValueOfEnum(enumClass = PropertyType.class, message = "Allowed types are HOUSE, APARTMENT, ROOM, NOT_SPECIFIED")
    protected String type = PropertyType.NOT_SPECIFIED.toString();

    @NotNull(message = "Enter address (can be blank)")
    @Size(max = 500, message = "Address should be 500 characters")
    private String address = "";

    @EmptyOrIntegerInRange(min = 1, max = 1000, message = "Minimum price should be empty or between 1 and 1000")
    private String priceMin = "";

    @EmptyOrIntegerInRange(min = 1, max = 1000, message = "Maximum price should be empty or between 1 and 1000")
    private String priceMax = "";

    @EmptyOrIntegerInRange(min = 1, max = 10, message = "Number of guests should be empty or between 1 and 10")
    protected String guests = "";

    @EmptyOrIntegerInRange(min = 1, max = 10, message = "Number of rooms should be empty or between 1 and 10")
    protected String rooms = "";

    @EmptyOrIntegerInRange(min = 1, max = 10, message = "Number of beds should be empty or between 1 and 10")
    protected String beds = "";

    @Pattern(regexp = "^(|true|false)$", message = "Should be empty or a boolean value true or false")
    protected String hasKitchen = "";

    @Pattern(regexp = "^(|true|false)$", message = "Should be empty or a boolean value true or false")
    protected String hasWasher = "";

    @Pattern(regexp = "^(|true|false)$", message = "Should be empty or a boolean value true or false")
    protected String hasTv = "";

    @Pattern(regexp = "^(|true|false)$", message = "Should be empty or a boolean value true or false")
    protected String hasInternet = "";

    @Pattern(regexp = "^(|true|false)$", message = "Should be empty or a boolean value true or false")
    protected String isPetsAllowed = "";

    @NotBlank(message = "Enter start date")
    @DatePresentOrFuture
    private String startDate = "";

    @NotBlank(message = "Enter end date")
    @DatePresentOrFuture
    private String endDate = "";
}