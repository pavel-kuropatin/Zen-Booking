package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyNotFoundException;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.PropertyType;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.request.PropertyRequest;
import com.kuropatin.bookingapp.model.response.PropertyResponse;
import com.kuropatin.bookingapp.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;
    private final UserService userService;
    private final ReviewService reviewService;

    public boolean canPropertyBeOrdered(LocalDate startDate, LocalDate endDate) {
        return repository.canPropertyBeOrdered(startDate, endDate);
    }

    public List<Property> getAllPropertyOfUser(Long userId) {
        return repository.findAllPropertyOfUser(userId);
    }

    public Property getPropertyByIdAndUserId(Long propertyId, Long userId) {
        if(repository.existsByIdAndUserId(propertyId, userId)) {
            return repository.findPropertyByIdAndOwnerId(propertyId, userId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property getPropertyById(Long propertyId, Long userId) {
        if(repository.existsByIdAndNotUserId(propertyId, userId)) {
            return repository.findPropertyByIdAndNotOwnerId(propertyId, userId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property createProperty(Long userId, PropertyRequest propertyRequest) {
        User user = userService.getUserById(userId);
        Property property = transformToNewProperty(propertyRequest);
        user.setProperty(Collections.singleton(property));
        property.setUser(user);
        property.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        property.setUpdated(property.getCreated());
        return repository.save(property);
    }

    public Property updateProperty(Long propertyId, Long userId, PropertyRequest propertyRequest) {
        Property propertyToUpdate = getPropertyByIdAndUserId(propertyId, userId);
        transformToProperty(propertyRequest, propertyToUpdate);
        propertyToUpdate.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(propertyToUpdate);
    }

    public String softDeletePropertyByIdAndUserId(Long propertyId, Long userId) {
        if(repository.existsByIdAndUserId(propertyId, userId)) {
            repository.softDeleteProperty(propertyId, Timestamp.valueOf(LocalDateTime.now()));
            return MessageFormat.format("Property with id: {0} successfully deleted", propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property transformToNewProperty(PropertyRequest propertyRequest) {
        Property property = new Property();
        transformToProperty(propertyRequest, property);
        return property;
    }

    public Property transformToProperty(PropertyRequest propertyRequest, Property property) {
        property.setType(PropertyType.valueOf(propertyRequest.getType()));
        property.setName(propertyRequest.getName());
        property.setDescription(propertyRequest.getDescription());
        property.setAddress(propertyRequest.getAddress());
        property.setPrice(Integer.parseInt(propertyRequest.getPrice()));
        property.setGuests(Short.parseShort(propertyRequest.getGuests()));
        property.setRooms(Short.parseShort(propertyRequest.getRooms()));
        property.setBeds(Short.parseShort(propertyRequest.getBeds()));
        property.setHasKitchen(Boolean.parseBoolean(propertyRequest.getHasKitchen()));
        property.setHasWasher(Boolean.parseBoolean(propertyRequest.getHasWasher()));
        property.setHasTv(Boolean.parseBoolean(propertyRequest.getHasTv()));
        property.setHasInternet(Boolean.parseBoolean(propertyRequest.getHasInternet()));
        property.setPetsAllowed(Boolean.parseBoolean(propertyRequest.getIsPetsAllowed()));
        property.setAvailable(Boolean.parseBoolean(propertyRequest.getIsAvailable()));
        return property;
    }

    public PropertyResponse transformToNewPropertyResponse(Property property) {
        PropertyResponse propertyResponse = new PropertyResponse();
        transformToPropertyResponse(property, propertyResponse);
        return propertyResponse;
    }

    public List<PropertyResponse> transformToListPropertyResponse(List<Property> properties) {
        List<PropertyResponse> propertyResponseList = new ArrayList<>();
        for(Property property : properties) {
            propertyResponseList.add(transformToNewPropertyResponse(property));
        }
        return propertyResponseList;
    }

    private PropertyResponse transformToPropertyResponse(Property property, PropertyResponse propertyResponse) {
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
        propertyResponse.setRating(reviewService.getRatingOfProperty(property.getId()));
        return propertyResponse;
    }
}