package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyRequest {

    private PropertyType type;
    private String name;
    private String description;
    private String address;
    private int price;
    private short guests;
    private short rooms;
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