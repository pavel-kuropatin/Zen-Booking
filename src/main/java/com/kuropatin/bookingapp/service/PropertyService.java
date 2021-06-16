package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyNotFoundException;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.dto.PropertyDto;
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

    public Property getPropertyById(Long propertyId) {
        if(repository.existsById(propertyId)) {
            return repository.findPropertyById(propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property createProperty(Long userId, PropertyDto propertyDto) {
        User user = userService.getUserById(userId);
        Property property = PropertyDto.transformToNewProperty(propertyDto);
        user.setProperty(Collections.singleton(property));
        property.setUser(user);
        property.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        property.setUpdated(property.getCreated());
        return repository.save(property);
    }

    public Property updateProperty(Long propertyId, PropertyDto propertyDto) {
        Property propertyToUpdate = getPropertyById(propertyId);
        PropertyDto.transformToProperty(propertyDto, propertyToUpdate);
        propertyToUpdate.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(propertyToUpdate);
    }

    public String softDeleteProperty(Long propertyId) {
        if(repository.existsById(propertyId)) {
            repository.softDeleteProperty(propertyId);
            return MessageFormat.format("Property with id: {0} successfully deleted", propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public List<Property> getAllNotApprovedProperty(){
        return repository.findAllNotApprovedProperty();
    }

    public Property getNotApprovedPropertyById(Long propertyId){
        if(repository.existsById(propertyId)) {
            return repository.findNotApprovedPropertyById(propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public List<Property> getAllBannedProperty(){
        return repository.findAllBannedProperty();
    }

    public Property getBannedPropertyById(Long propertyId){
        if(repository.existsById(propertyId)) {
            return repository.findBannedPropertyById(propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property approveProperty(Long propertyId){
        if(repository.existsById(propertyId)) {
            return repository.approveProperty(propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property disapproveProperty(Long propertyId){
        if(repository.existsById(propertyId)) {
            return repository.disapproveProperty(propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property banProperty(Long propertyId) {
        if(repository.existsById(propertyId)) {
            return repository.banProperty(propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }

    public Property unbanProperty(Long propertyId) {
        if(repository.existsById(propertyId)) {
            return repository.unbanProperty(propertyId);
        } else {
            throw new PropertyNotFoundException(propertyId);
        }
    }
}