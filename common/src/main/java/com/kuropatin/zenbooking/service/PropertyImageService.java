package com.kuropatin.zenbooking.service;

import com.kuropatin.zenbooking.exception.PropertyImageNotFoundException;
import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.model.PropertyImage;
import com.kuropatin.zenbooking.model.request.PropertyImageRequest;
import com.kuropatin.zenbooking.model.response.PropertyImageResponse;
import com.kuropatin.zenbooking.model.response.SuccessfulResponse;
import com.kuropatin.zenbooking.repository.PropertyImageRepository;
import com.kuropatin.zenbooking.util.ApplicationTimeUtils;
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

    public List<PropertyImage> getAllImagesOfPropertyByIdAndUserId(final Long propertyId, final Long userId) {
        return repository.findAllImagesOfPropertyOfUser(propertyId, userId);
    }

    public List<PropertyImage> getAllImagesOfPropertyById(final Long propertyId) {
        return repository.findAllImagesOfProperty(propertyId);
    }

    public PropertyImage getImageOfPropertyByIdAndPropertyIdAndUserId(final Long imageId, final Long propertyId, final Long userId) {
        if (repository.existsByIdAndPropertyIdAndUserId(imageId, propertyId, userId)) {
            return repository.findPropertyImageByIdAndPropertyIdAndUserId(imageId, propertyId, userId);
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public PropertyImage getImageOfPropertyByIdAndPropertyId(final Long imageId, final Long propertyId) {
        if (repository.existsByIdAndPropertyId(imageId, propertyId)) {
            return repository.findPropertyImageByIdAndPropertyId(imageId, propertyId);
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public PropertyImage create(final Long propertyId, final Long userId, final PropertyImageRequest propertyImageRequest) {
        final Property property = propertyService.getPropertyByIdAndUserId(propertyId, userId);
        final PropertyImage propertyImage = new PropertyImage();
        propertyImage.setImgUrl(propertyImageRequest.getImgUrl());
        final Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
        propertyImage.setCreated(timestamp);
        propertyImage.setUpdated(timestamp);
        property.addPropertyImage(propertyImage);
        propertyImage.setProperty(property);
        return repository.save(propertyImage);
    }

    public SuccessfulResponse softDeletePropertyImageByIdAndPropertyIdAndUserId(final Long imageId, final Long propertyId, final Long userId) {
        if (repository.existsByIdAndPropertyIdAndUserId(imageId, propertyId, userId)) {
            final Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
            repository.softDeletePropertyImage(imageId, timestamp);
            return new SuccessfulResponse(timestamp, MessageFormat.format("Image with id: {0} successfully deleted", imageId));
        } else {
            throw new PropertyImageNotFoundException(imageId);
        }
    }

    public PropertyImageResponse transformToNewPropertyImageResponse(final PropertyImage propertyImage) {
        return transformToPropertyImageResponse(propertyImage, new PropertyImageResponse());
    }

    public List<PropertyImageResponse> transformToListPropertyImageResponse(final List<PropertyImage> propertyImages) {
        final List<PropertyImageResponse> propertyImageResponseList = new ArrayList<>();
        for (final PropertyImage propertyImage : propertyImages) {
            propertyImageResponseList.add(transformToNewPropertyImageResponse(propertyImage));
        }
        return propertyImageResponseList;
    }

    private PropertyImageResponse transformToPropertyImageResponse(final PropertyImage propertyImage, final PropertyImageResponse propertyImageResponse) {
        propertyImageResponse.setId(propertyImage.getId());
        propertyImageResponse.setImgUrl(propertyImage.getImgUrl());
        return propertyImageResponse;
    }
}