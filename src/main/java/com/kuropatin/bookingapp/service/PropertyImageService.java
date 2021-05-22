package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyImageNotFoundException;
import com.kuropatin.bookingapp.model.PropertyImage;
import com.kuropatin.bookingapp.repository.PropertyImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropertyImageService {

    private final PropertyImageRepository repository;

    @Autowired
    public PropertyImageService(PropertyImageRepository repository) {
        this.repository = repository;
    }

    public List<PropertyImage> getAllImagesOfProperty(Long propertyId) {
        return repository.getAllImagesOfProperty(propertyId);
    }

    public PropertyImage getImageOfPropertyById(Long propertyId, Long imageId) {
        return repository.getImageOfPropertyById(propertyId, imageId);
    }

    public PropertyImage getPropertyImageById(Long imageId) {
        return repository.findById(imageId).orElseThrow(() -> new PropertyImageNotFoundException(imageId));
    }

    public PropertyImage create(Long userId, PropertyImage propertyImage) {
        propertyImage.setPropertyId(userId);
        return repository.save(propertyImage);
    }

    @Transactional
    public PropertyImage update(Long id, PropertyImage propertyImage) {
        PropertyImage propertyImageToUpdate = getPropertyImageById(id);
        propertyImageToUpdate.setImgUrl(propertyImage.getImgUrl());
        return propertyImageToUpdate;
    }

    @Transactional
    public PropertyImage deleteById(Long id) {
        PropertyImage propertyImageToDelete = getPropertyImageById(id);
        repository.delete(propertyImageToDelete);
        return propertyImageToDelete;
    }
}