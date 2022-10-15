package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.util.ToStringUtils;
import com.kuropatin.zenbooking.validation.IntegerInRange;
import com.kuropatin.zenbooking.validation.NullableBoolean;
import com.kuropatin.zenbooking.validation.ShortInRange;
import com.kuropatin.zenbooking.validation.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequest implements Request {

    @NotBlank(message = "Enter property name")
    @Size(min = 2, max = 100, message = "Property name should be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Enter property type")
    @ValueOfEnum(enumClass = PropertyType.class, message = "Allowed types are HOUSE, APARTMENT, ROOM, NOT_SPECIFIED")
    protected String type;

    @NotBlank(message = "Enter property description")
    @Size(min = 2, max = 500, message = "Property description should be between 2 and 500 characters")
    private String description;

    @NotBlank(message = "Enter address")
    @Size(max = 500, message = "Address should be between 500 characters or lower")
    private String address;

    @IntegerInRange(min = 1, max = 1000, message = "Enter price, should be between 1 and 1000")
    private Integer price;

    @ShortInRange(min = 1, max = 10, message = "Enter number of guests, should be between 1 and 10")
    protected Short guests;

    @ShortInRange(min = 1, max = 10, message = "Enter rooms of rooms, should be between 1 and 10")
    protected Short rooms;

    @ShortInRange(min = 1, max = 10, message = "Enter number of beds, should be between 1 and 10")
    protected Short beds;

    @NotBlank(message = "Enter if property has kitchen")
    @NullableBoolean
    protected Boolean hasKitchen;

    @NotBlank(message = "Enter if property has washer")
    @NullableBoolean
    protected Boolean hasWasher;

    @NotBlank(message = "Enter if property has TV")
    @NullableBoolean
    protected Boolean hasTv;

    @NotBlank(message = "Enter if property has internet")
    @NullableBoolean
    protected Boolean hasInternet;

    @NotBlank(message = "Enter if pets allowed")
    @NullableBoolean
    protected Boolean isPetsAllowed;

    @NotBlank(message = "Enter if property available for booking")
    @NullableBoolean
    private Boolean isAvailable;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}