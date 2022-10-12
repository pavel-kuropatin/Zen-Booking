package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.validation.DatePresentOrFuture;
import com.kuropatin.zenbooking.validation.EmptyOrBigger;
import com.kuropatin.zenbooking.validation.EmptyOrIntegerInRange;
import com.kuropatin.zenbooking.validation.EmptyOrShortInRange;
import com.kuropatin.zenbooking.validation.NullableBoolean;
import com.kuropatin.zenbooking.validation.ValueOfEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private Integer priceMin;

    @EmptyOrIntegerInRange(min = 1, max = 1000, message = "Maximum price should be empty or between 1 and 1000")
    private Integer priceMax;

    @EmptyOrShortInRange(min = 1, max = 10, message = "Number of guests should be empty or between 1 and 10")
    protected Short guests;

    @EmptyOrShortInRange(min = 1, max = 10, message = "Number of rooms should be empty or between 1 and 10")
    protected Short rooms;

    @EmptyOrShortInRange(min = 1, max = 10, message = "Number of beds should be empty or between 1 and 10")
    protected Short beds;

    @NullableBoolean
    protected Boolean hasKitchen;

    @NullableBoolean
    protected Boolean hasWasher;

    @NullableBoolean
    protected Boolean hasTv;

    @NullableBoolean
    protected Boolean hasInternet;

    @NullableBoolean
    protected Boolean isPetsAllowed;

    @NotBlank(message = "Enter start date")
    @DatePresentOrFuture
    private String startDate;

    @NotBlank(message = "Enter end date")
    @DatePresentOrFuture
    private String endDate;

    @EmptyOrIntegerInRange(min = 1, max = 100, message = "Number of property per page should be empty or between 1 and 100")
    private Integer perPage;

    @EmptyOrBigger(min = 0, message = "Page number should be empty or bigger than 0")
    private Integer pageNumber;
}