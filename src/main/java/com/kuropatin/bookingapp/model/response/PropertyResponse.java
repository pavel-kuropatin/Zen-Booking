package com.kuropatin.bookingapp.model.response;

import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyResponse {

    private long id;
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

    public static PropertyResponse transformToNewPropertyResponse(Property property) {
        PropertyResponse propertyResponse = new PropertyResponse();
        transformToPropertyResponse(property, propertyResponse);
        return propertyResponse;
    }

    public static List<PropertyResponse> transformToListPropertyResponse(List<Property> properties) {
        List<PropertyResponse> propertyResponseList = new ArrayList<>();
        for(Property property : properties) {
            propertyResponseList.add(transformToNewPropertyResponse(property));
        }
        return propertyResponseList;
    }

    private static PropertyResponse transformToPropertyResponse(Property property, PropertyResponse propertyResponse) {
        propertyResponse.setId(property.getId());
        propertyResponse.setType(property.getType());
        propertyResponse.setName(property.getName());
        propertyResponse.setDescription(property.getDescription());
        propertyResponse.setAddress(property.getAddress());
        propertyResponse.setPrice(property.getPrice());
        propertyResponse.setGuests(property.getGuests());
        propertyResponse.setRooms(property.getRooms());
        propertyResponse.setBeds(property.getBeds());
        propertyResponse.setHasKitchen(property.isHasKitchen());
        propertyResponse.setHasWasher(property.isHasWasher());
        propertyResponse.setHasTv(property.isHasTv());
        propertyResponse.setHasInternet(property.isHasInternet());
        propertyResponse.setPetsAllowed(property.isPetsAllowed());
        propertyResponse.setAvailable(property.isAvailable());
        return propertyResponse;
    }
}