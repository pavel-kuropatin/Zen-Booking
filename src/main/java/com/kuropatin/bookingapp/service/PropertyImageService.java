package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.PropertyImageNotFoundException;
import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.PropertyImage;
import com.kuropatin.bookingapp.model.request.PropertyImageRequest;
import com.kuropatin.bookingapp.model.response.PropertyImageResponse;
import com.kuropatin.bookingapp.model.response.SuccessfulResponse;
import com.kuropatin.bookingapp.repository.PropertyImageRepository;
import com.kuropatin.bookingapp.util.ApplicationTimestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyImageService {

    private final PropertyImageRepository repository;
    private final PropertyService propertyService;

    public List<PropertyImage> getAllImagesOfPropertyByIdAndUserId(Long propertyId, Long userId) {
        return repository.findAllImagesOfPropertyOfUser(propertyId, userId);
    }

    public List<PropertyImage> getAllImagesOfPropertyById(Long propertyId) {
        return repository.findAllImagesOfProperty(propertyId);
    }

    public PropertyImage getImageOfPropertyByIdAndPropertyIdAndUserId(Long imageId, Long propertyId, Long userId) {
        if(repository.existsByIdAndPropertyIdAndUserId(imageId, propertyId, userId)) {
            return repository.findPropertyImageByIdAndPropertyIdAndUserId(imageId, propertyId, userId);
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public PropertyImage getImageOfPropertyByIdAndPropertyId(Long imageId, Long propertyId) {
        if(repository.existsByIdAndPropertyId(imageId, propertyId)) {
            return repository.findPropertyImageByIdAndPropertyId(imageId, propertyId);
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public PropertyImage create(Long propertyId, Long userId, PropertyImageRequest propertyImageRequest) {
        Property property = propertyService.getPropertyByIdAndUserId(propertyId, userId);
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setImgUrl(propertyImageRequest.getImgUrl());
        Timestamp timestamp = ApplicationTimestamp.getTimestampUTC();
        propertyImage.setCreated(timestamp);
        propertyImage.setUpdated(timestamp);
        property.addPropertyImage(propertyImage);
        propertyImage.setProperty(property);
        return repository.save(propertyImage);
    }

    public SuccessfulResponse softDeletePropertyImageByIdAndPropertyIdAndUserId(Long imageId, Long propertyId, Long userId) {
        if(repository.existsByIdAndPropertyIdAndUserId(imageId, propertyId, userId)) {
            Timestamp timestamp = ApplicationTimestamp.getTimestampUTC();
            repository.softDeletePropertyImage(imageId, timestamp);
            return new SuccessfulResponse(timestamp, MessageFormat.format("Image with id: {0} successfully deleted", imageId));
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public PropertyImageResponse transformToNewPropertyImageResponse(PropertyImage propertyImage) {
        return transformToPropertyImageResponse(propertyImage, new PropertyImageResponse());
    }

    public List<PropertyImageResponse> transformToListPropertyImageResponse(List<PropertyImage> propertyImages) {
        List<PropertyImageResponse> propertyImageResponseList = new ArrayList<>();
        for(PropertyImage propertyImage : propertyImages) {
            propertyImageResponseList.add(transformToNewPropertyImageResponse(propertyImage));
        }
        return propertyImageResponseList;
    }

    private PropertyImageResponse transformToPropertyImageResponse(PropertyImage propertyImage, PropertyImageResponse propertyImageResponse) {
        propertyImageResponse.setId(propertyImage.getId());
        propertyImageResponse.setImgUrl(propertyImage.getImgUrl());
        return propertyImageResponse;
    }
}