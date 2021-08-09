package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyNotFoundException;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.request.PropertyRequest;
import com.kuropatin.bookingapp.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;
    private final UserService userService;

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
        Property property = PropertyRequest.transformToNewProperty(propertyRequest);
        user.setProperty(Collections.singleton(property));
        property.setUser(user);
        property.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        property.setUpdated(property.getCreated());
        return repository.save(property);
    }

    public Property updateProperty(Long propertyId, Long userId, PropertyRequest propertyRequest) {
        Property propertyToUpdate = getPropertyByIdAndUserId(propertyId, userId);
        PropertyRequest.transformToProperty(propertyRequest, propertyToUpdate);
        propertyToUpdate.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(propertyToUpdate);
    }

    public String softDeletePropertyByIdAndUserId(Long propertyId, Long userId) {
        if(repository.existsByIdAndUserId(propertyId, userId)) {
            repository.softDeleteProperty(propertyId);
            return MessageFormat.format("Property with id: {0} successfully deleted", propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }
}