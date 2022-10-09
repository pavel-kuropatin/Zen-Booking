package com.kuropatin.zenbooking.service;

import com.kuropatin.zenbooking.exception.PropertyNotFoundException;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.model.User;
import com.kuropatin.zenbooking.model.request.PropertyRequest;
import com.kuropatin.zenbooking.model.response.PropertyResponse;
import com.kuropatin.zenbooking.model.response.SuccessfulResponse;
import com.kuropatin.zenbooking.repository.PropertyRepository;
import com.kuropatin.zenbooking.repository.ReviewRepository;
import com.kuropatin.zenbooking.util.ApplicationTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public boolean canPropertyBeOrdered(final LocalDate startDate, final LocalDate endDate, final Long propertyId) {
        return repository.canPropertyBeOrdered(startDate, endDate, propertyId);
    }

    public List<Property> getAllPropertyOfUser(final Long userId) {
        return repository.findAllPropertyOfUser(userId);
    }

    public Property getPropertyByIdAndUserId(final Long propertyId, final Long userId) {
        if (repository.existsByIdAndUserId(propertyId, userId)) {
            return repository.findPropertyByIdAndOwnerId(propertyId, userId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property getPropertyById(final Long propertyId, final Long userId) {
        if (repository.existsByIdAndNotUserId(propertyId, userId)) {
            return repository.findPropertyByIdAndNotOwnerId(propertyId, userId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property createProperty(final Long userId, final PropertyRequest propertyRequest) {
        final User user = userService.getUserById(userId);
        final Property property = transformToNewProperty(propertyRequest);
        user.addProperty(property);
        property.setUser(user);
        final Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
        property.setCreated(timestamp);
        property.setUpdated(timestamp);
        return repository.save(property);
    }

    public Property updateProperty(final Long propertyId, final Long userId, final PropertyRequest propertyRequest) {
        final Property propertyToUpdate = getPropertyByIdAndUserId(propertyId, userId);
        transformToProperty(propertyRequest, propertyToUpdate);
        propertyToUpdate.setUpdated(ApplicationTimeUtils.getTimestamp());
        return repository.save(propertyToUpdate);
    }

    public SuccessfulResponse softDeletePropertyByIdAndUserId(final Long propertyId, final Long userId) {
        if (repository.existsByIdAndUserId(propertyId, userId)) {
            Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
            repository.softDeleteProperty(propertyId, timestamp);
            return new SuccessfulResponse(timestamp, MessageFormat.format("Property with id: {0} successfully deleted", propertyId));
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Double getRatingOfProperty(final Long propertyId) {
        final Optional<Double> rating = reviewRepository.getRatingOfProperty(propertyId);
        return rating.map(aDouble -> Math.round(aDouble * 10) / 10.0).orElse(null);
    }

    public Property transformToNewProperty(final PropertyRequest propertyRequest) {
        return transformToProperty(propertyRequest, new Property());
    }

    private Property transformToProperty(final PropertyRequest propertyRequest,final  Property property) {
        property.setType(PropertyType.valueOf(propertyRequest.getType()));
        property.setName(propertyRequest.getName());
        property.setDescription(propertyRequest.getDescription());
        property.setAddress(propertyRequest.getAddress());
        property.setPrice(propertyRequest.getPrice());
        property.setGuests(propertyRequest.getGuests());
        property.setRooms(propertyRequest.getRooms());
        property.setBeds(propertyRequest.getBeds());
        property.setHasKitchen(propertyRequest.getHasKitchen());
        property.setHasWasher(propertyRequest.getHasWasher());
        property.setHasTv(propertyRequest.getHasTv());
        property.setHasInternet(propertyRequest.getHasInternet());
        property.setIsPetsAllowed(propertyRequest.getIsPetsAllowed());
        property.setIsAvailable(propertyRequest.getIsAvailable());
        return property;
    }

    public PropertyResponse transformToNewPropertyResponse(final Property property) {
        return transformToPropertyResponse(property, new PropertyResponse());
    }

    public List<PropertyResponse> transformToListPropertyResponse(final List<Property> properties) {
        final List<PropertyResponse> propertyResponseList = new ArrayList<>();
        for (final Property property : properties) {
            propertyResponseList.add(transformToNewPropertyResponse(property));
        }
        return propertyResponseList;
    }

    private PropertyResponse transformToPropertyResponse(final Property property, final PropertyResponse propertyResponse) {
        propertyResponse.setId(property.getId());
        propertyResponse.setType(property.getType());
        propertyResponse.setName(property.getName());
        propertyResponse.setDescription(property.getDescription());
        propertyResponse.setAddress(property.getAddress());
        propertyResponse.setPrice(property.getPrice());
        propertyResponse.setGuests(property.getGuests());
        propertyResponse.setRooms(property.getRooms());
        propertyResponse.setBeds(property.getBeds());
        propertyResponse.setHasKitchen(property.getHasKitchen());
        propertyResponse.setHasWasher(property.getHasWasher());
        propertyResponse.setHasTv(property.getHasTv());
        propertyResponse.setHasInternet(property.getHasInternet());
        propertyResponse.setIsPetsAllowed(property.getIsPetsAllowed());
        propertyResponse.setIsAvailable(property.getIsAvailable());
        propertyResponse.setRating(getRatingOfProperty(property.getId()));
        return propertyResponse;
    }
}