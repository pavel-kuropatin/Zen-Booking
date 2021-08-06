package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.PropertyType;
import com.kuropatin.bookingapp.validation.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyRequest {

    @ValueOfEnum(enumClass = PropertyType.class, message = "Specify type")
    private PropertyType type;

    @NotEmpty(message = "Enter property name")
    @NotBlank(message = "Enter property name")
    @Size(min = 2, max = 100, message = "Property name should be between 2 and 100 characters")
    private String name;

    @NotEmpty(message = "Enter property description")
    @NotBlank(message = "Enter property description")
    @Size(min = 2, max = 500, message = "Property description should be between 2 and 500 characters")
    private String description;

    @NotEmpty(message = "Enter address")
    @NotBlank(message = "Enter address")
    @Size(max = 500, message = "Address should be between 500 characters or lower")
    private String address;

    @Positive(message = "Price should be a positive number")
    @Max(value = 1000, message = "Max price is 1000")
    private int price;

    @Positive(message = "Number of guests should be a positive number")
    @Max(value = 10, message = "Max number of guests is 10")
    private short guests;

    @Positive(message = "Number of rooms should be a positive number")
    @Max(value = 10, message = "Max number of rooms value is 10")
    private short rooms;

    @Positive(message = "Number of beds should be a positive number")
    @Max(value = 10, message = "Max number of beds is 10")
    private short beds;

    private boolean hasKitchen;
    private boolean hasWasher;
    private boolean hasTv;
    private boolean hasInternet;
    private boolean isPetsAllowed;
    private boolean isAvailable;

    public static Property transformToNewProperty(PropertyRequest propertyRequest) {
        Property property = new Property();
        transformToProperty(propertyRequest, property);
        return property;
    }

    public static Property transformToProperty(PropertyRequest propertyRequest, Property property) {
        property.setType(propertyRequest.getType());
        property.setName(propertyRequest.getName());
        property.setDescription(propertyRequest.getDescription());
        property.setAddress(propertyRequest.getAddress());
        property.setPrice(propertyRequest.getPrice());
        property.setGuests(propertyRequest.getGuests());
        property.setRooms(propertyRequest.getRooms());
        property.setBeds(propertyRequest.getBeds());
        property.setHasKitchen(propertyRequest.isHasKitchen());
        property.setHasWasher(propertyRequest.isHasWasher());
        property.setHasTv(propertyRequest.isHasTv());
        property.setHasInternet(propertyRequest.isHasInternet());
        property.setPetsAllowed(propertyRequest.isPetsAllowed());
        property.setAvailable(propertyRequest.isAvailable());
        return property;
    }
}