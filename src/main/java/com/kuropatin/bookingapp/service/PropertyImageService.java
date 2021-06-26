package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyImageNotFoundException;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.PropertyImage;
import com.kuropatin.bookingapp.model.request.PropertyImageRequest;
import com.kuropatin.bookingapp.repository.PropertyImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyImageService {

    private final PropertyImageRepository repository;
    private final PropertyService propertyService;

    public List<PropertyImage> getAllImagesOfProperty(Long propertyId) {
        return repository.findAllPropertyImages(propertyId);
    }

    public PropertyImage getPropertyImageById(Long imageId) {
        if(repository.existsById(imageId)) {
            return repository.findPropertyImageById(imageId);
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public PropertyImage create(Long userId, PropertyImageRequest propertyImageRequest) {
        Property property = propertyService.getPropertyById(userId);
        PropertyImage propertyImage = new PropertyImage();
        property.setPropertyImages(Collections.singleton(propertyImage));
        propertyImage.setProperty(property);
        propertyImage.setImgUrl(propertyImageRequest.getImgUrl());
        propertyImage.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        propertyImage.setUpdated(property.getCreated());
        return repository.save(propertyImage);
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