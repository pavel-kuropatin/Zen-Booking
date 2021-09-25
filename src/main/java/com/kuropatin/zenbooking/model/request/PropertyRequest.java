package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.validation.IntegerInRange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class PropertyRequest extends BasicPropertyRequest {

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

    @NotBlank(message = "Enter if property available for booking")
    @Pattern(regexp = "true|false", message = "Should be a boolean value true or false")
    private String isAvailable;
}