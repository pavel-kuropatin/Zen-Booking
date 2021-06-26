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

    private PropertyType type;
    private String name;
    private String description;
    private String country;
    private String region;
    private String city;
    private String street;
    private String building;
    private String apartment;
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
    private boolean isApproved;

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

    public static PropertyResponse transformToPropertyResponse(Property property, PropertyResponse propertyResponse) {
        propertyResponse.setType(property.getType());
        propertyResponse.setName(property.getName());
        propertyResponse.setDescription(property.getDescription());
        propertyResponse.setCountry(property.getCountry());
        propertyResponse.setRegion(property.getRegion());
        propertyResponse.setCity(property.getCity());
        propertyResponse.setStreet(property.getStreet());
        propertyResponse.setBuilding(property.getBuilding());
        propertyResponse.setApartment(property.getApartment());
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
        propertyResponse.setApproved(property.isApproved());
        return propertyResponse;
    }
}