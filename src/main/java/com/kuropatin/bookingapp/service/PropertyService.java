package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyNotFoundException;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository repository;
//    private final UserService userService;

    @Autowired
    public PropertyService(PropertyRepository repository) {
        this.repository = repository;
//        this.userService = userService;
    }

    public List<Property> getAllPropertyOfUser(Long userId) {
        return repository.getAllPropertyOfUser(userId);
    }

    public Property getPropertyOfUserById(Long userId, Long propertyId) {
        return repository.getPropertyOfUserById(userId,propertyId);
    }

    public Property getPropertyById(Long propertyId) {
        return repository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
    }

    public Property create(Long userId, Property property) {
        property.setUserId(userId);
        return repository.save(property);
    }

    @Transactional
    public Property update(Long id, Property property) {
        Property propertyToUpdate = getPropertyById(id);
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
        return propertyToUpdate;
    }

    @Transactional
    public Property deleteById(Long id) {
        Property propertyToDelete = getPropertyById(id);
        repository.delete(propertyToDelete);
        return propertyToDelete;
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