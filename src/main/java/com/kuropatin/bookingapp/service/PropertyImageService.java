package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyImageNotFoundException;
import com.kuropatin.bookingapp.exception.PropertyNotFoundException;
import com.kuropatin.bookingapp.model.PropertyImage;
import com.kuropatin.bookingapp.repository.PropertyImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class PropertyImageService {

    private final PropertyImageRepository repository;

    @Autowired
    public PropertyImageService(PropertyImageRepository repository) {
        this.repository = repository;
    }

    public List<PropertyImage> getAllImagesOfProperty(Long propertyId) {
        return repository.findAllImagesOfProperty(propertyId);
    }

    public PropertyImage getPropertyImageById(Long imageId) {
        if(repository.existsById(imageId)) {
            return repository.findPropertyImageById(imageId);
        } else {
            throw new PropertyNotFoundException(imageId);
        }
    }

    public PropertyImage create(Long userId, PropertyImage propertyImage) {
        propertyImage.setPropertyId(userId);
        return repository.save(propertyImage);
    }

    public PropertyImage update(Long id, PropertyImage propertyImage) {
        PropertyImage propertyImageToUpdate = getPropertyImageById(id);
        if(propertyImage.equals(propertyImageToUpdate)) {
            return propertyImageToUpdate;
        } else {
            propertyImageToUpdate.setImgUrl(propertyImage.getImgUrl());
            return propertyImageToUpdate;
        }
    }

    public String softDeletePropertyImage(Long imageId) {
        if(repository.existsById(imageId)) {
            repository.softDeletePropertyImage(imageId);
            return MessageFormat.format("Property Image with id: {0} successfully deleted", imageId);
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public List<PropertyImage> getAllNotApprovedPropertyImage(){
        return repository.findAllNotApprovedPropertyImage();
    }

    public PropertyImage getNotApprovedPropertyImageById(Long imageId){
        if(repository.existsById(imageId)) {
            return repository.findNotApprovedPropertyImageById(imageId);
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public PropertyImage approvePropertyImage(Long imageId){
        if(repository.existsById(imageId)) {
            return repository.approvePropertyImage(imageId);
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public PropertyImage disapprovePropertyImage(Long imageId){
        if(repository.existsById(imageId)) {
            return repository.disapprovePropertyImage(imageId);
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }
}