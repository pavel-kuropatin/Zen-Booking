package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyNotFoundException;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository repository;

    @Autowired
    public PropertyService(PropertyRepository repository) {
        this.repository = repository;
    }

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
        property.setUserId(userId);
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
            propertyToUpdate.setKitchen(property.isKitchen());
            propertyToUpdate.setWasher(property.isWasher());
            propertyToUpdate.setTv(property.isTv());
            propertyToUpdate.setInternet(property.isInternet());
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

//    @Transactional
//    public User addPropertyToUser(Long propertyId, Long userId) {
//        User user = userService.getUserById(userId);
//        Property property = getPropertyById(propertyId);
//        user.addProperty(property);
//        property.setUser(user);
//        return user;
//    }
//
//    @Transactional
//    public User removePropertyFromUser(Long propertyId, Long userId) {
//        User user = userService.getUserById(userId);
//        Property property = getPropertyById(propertyId);
//        user.removeProperty(property);
//        property.removeUser(user);
//        return user;
//    }
}