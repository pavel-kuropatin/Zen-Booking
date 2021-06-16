package com.kuropatin.bookingapp.model.dto;

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
public class PropertyDto {

    private PropertyType type = PropertyType.OTHER;
    private String name;
    private String description;
    private String country;
    private String region;
    private String city;
    private String street;
    private String building;
    private String apartment = "";
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

    public static Property transformToNewProperty(PropertyDto propertyDto) {
        Property property = new Property();
        transformToProperty(propertyDto, property);
        return property;
    }

    public static Property transformToProperty(PropertyDto propertyDto, Property property) {
        property.setType(propertyDto.getType());
        property.setName(propertyDto.getName());
        property.setDescription(propertyDto.getDescription());
        property.setCountry(propertyDto.getCountry());
        property.setRegion(propertyDto.getRegion());
        property.setCity(propertyDto.getCity());
        property.setStreet(propertyDto.getStreet());
        property.setBuilding(propertyDto.getBuilding());
        property.setApartment(propertyDto.getApartment());
        property.setPrice(propertyDto.getPrice());
        property.setGuests(propertyDto.getGuests());
        property.setRooms(propertyDto.getRooms());
        property.setBeds(propertyDto.getBeds());
        property.setHasKitchen(propertyDto.isHasKitchen());
        property.setHasWasher(propertyDto.isHasWasher());
        property.setHasTv(propertyDto.isHasTv());
        property.setHasInternet(propertyDto.isHasInternet());
        property.setPetsAllowed(propertyDto.isPetsAllowed());
        property.setAvailable(propertyDto.isAvailable());
        return property;
    }
}