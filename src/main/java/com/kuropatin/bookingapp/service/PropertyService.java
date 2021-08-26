package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyNotFoundException;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.PropertyType;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.request.PropertyRequest;
import com.kuropatin.bookingapp.model.response.PropertyResponse;
import com.kuropatin.bookingapp.model.response.SuccessfulResponse;
import com.kuropatin.bookingapp.repository.PropertyRepository;
import com.kuropatin.bookingapp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public boolean canPropertyBeOrdered(LocalDate startDate, LocalDate endDate, Long propertyId) {
        return repository.canPropertyBeOrdered(startDate, endDate, propertyId);
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
        user.addProperty(property);
        property.setUser(user);
        property.setCreated(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        property.setUpdated(property.getCreated());
        return repository.save(property);
    }

    public Property updateProperty(Long propertyId, Long userId, PropertyRequest propertyRequest) {
        Property propertyToUpdate = getPropertyByIdAndUserId(propertyId, userId);
        transformToProperty(propertyRequest, propertyToUpdate);
        propertyToUpdate.setUpdated(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        return repository.save(propertyToUpdate);
    }

    public SuccessfulResponse softDeletePropertyByIdAndUserId(Long propertyId, Long userId) {
        if(repository.existsByIdAndUserId(propertyId, userId)) {
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));
            repository.softDeleteProperty(propertyId, timestamp);
            return new SuccessfulResponse(timestamp, MessageFormat.format("Property with id: {0} successfully deleted", propertyId));
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public String getRatingOfProperty(Long propertyId) {
        Optional<Double> rating = reviewRepository.getRatingOfProperty(propertyId);
        return rating.map(aDouble -> String.valueOf(Math.round(aDouble * 10) / 10.0)).orElse("n/a");
    }

    public Property transformToNewProperty(PropertyRequest propertyRequest) {
        return transformToProperty(propertyRequest, new Property());
    }

    private Property transformToProperty(PropertyRequest propertyRequest, Property property) {
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
        return transformToPropertyResponse(property, new PropertyResponse());
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
        propertyResponse.setRating(getRatingOfProperty(property.getId()));
        return propertyResponse;
    }
}