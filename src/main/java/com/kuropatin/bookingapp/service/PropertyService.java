package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyNotFoundException;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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

    public Property createProperty(Long userId, Property property) {
        User user = userService.getUserById(userId);
        user.setProperty(Collections.singleton(property));
        property.setUser(user);
        return repository.save(property);
    }

    public Property updateProperty(Long propertyId, Property property) {
        Property propertyToUpdate = getPropertyById(propertyId);
        if(property.equals(propertyToUpdate)) {
            return propertyToUpdate;
        } else {
            propertyToUpdate.setType(property.getType());
            propertyToUpdate.setName(property.getName());
            propertyToUpdate.setDescription(property.getDescription());
            propertyToUpdate.setCountry(property.getCountry());
            propertyToUpdate.setRegion(property.getRegion());
            propertyToUpdate.setCity(property.getCity());
            propertyToUpdate.setStreet(property.getStreet());
            propertyToUpdate.setBuilding(property.getBuilding());
            propertyToUpdate.setApartment(property.getApartment());
            propertyToUpdate.setPrice(property.getPrice());
            propertyToUpdate.setGuests(property.getGuests());
            propertyToUpdate.setRooms(property.getRooms());
            propertyToUpdate.setBeds(property.getBeds());
            propertyToUpdate.setHasKitchen(property.hasKitchen());
            propertyToUpdate.setHasWasher(property.hasWasher());
            propertyToUpdate.setHasTv(property.hasTv());
            propertyToUpdate.setHasInternet(property.hasInternet());
            propertyToUpdate.setPetsAllowed(property.isPetsAllowed());
            propertyToUpdate.setAvailable(property.isAvailable());
            return repository.save(propertyToUpdate);
        }
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