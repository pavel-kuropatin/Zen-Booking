package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.PropertyImageRequest;
import com.kuropatin.bookingapp.model.request.PropertyRequest;
import com.kuropatin.bookingapp.model.response.PropertyImageResponse;
import com.kuropatin.bookingapp.model.response.PropertyResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.PropertyImageService;
import com.kuropatin.bookingapp.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hosting")
@RequiredArgsConstructor
public class HostingController {

    private final PropertyService propertyService;
    private final PropertyImageService propertyImageService;
    private final AuthenticationUtils authenticationUtils;

    @GetMapping("/property")
    public ResponseEntity<List<PropertyResponse>> getAllProperty() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyResponse.transformToListPropertyResponse(propertyService.getAllPropertyOfUser(userId)), HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable final Long propertyId) {
        return new ResponseEntity<>(PropertyResponse.transformToNewPropertyResponse(propertyService.getPropertyById(propertyId)), HttpStatus.OK);
    }

    @PostMapping("/property")
    public ResponseEntity<PropertyResponse> createProperty(@RequestBody final PropertyRequest propertyRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyResponse.transformToNewPropertyResponse(propertyService.createProperty(userId, propertyRequest)), HttpStatus.CREATED);
    }

    @PutMapping("/property/{propertyId}")
    public ResponseEntity<PropertyResponse> updateProperty(@PathVariable final Long propertyId, @RequestBody final PropertyRequest propertyRequest) {
        return new ResponseEntity<>(PropertyResponse.transformToNewPropertyResponse(propertyService.updateProperty(propertyId, propertyRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<String> deletePropertyById(@PathVariable final Long propertyId) {
        return new ResponseEntity<>(propertyService.softDeleteProperty(propertyId), HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}/images")
    public ResponseEntity<List<PropertyImageResponse>> getAllPropertyImages(@PathVariable final Long propertyId) {
        return new ResponseEntity<>(PropertyImageResponse.transformToListPropertyImageResponse(propertyImageService.getAllImagesOfProperty(propertyId)), HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}/images/{imageId}")
    public ResponseEntity<PropertyImageResponse> getPropertyImagesById(@PathVariable final Long imageId) {
        return new ResponseEntity<>(PropertyImageResponse.transformToNewPropertyImageResponse(propertyImageService.getPropertyImageById(imageId)), HttpStatus.OK);
    }

    @PostMapping("/property/{propertyId}/images")
    public ResponseEntity<PropertyImageResponse> createPropertyImage(@PathVariable final Long propertyId, @RequestBody final PropertyImageRequest propertyImageRequest) {
        return new ResponseEntity<>(PropertyImageResponse.transformToNewPropertyImageResponse(propertyImageService.create(propertyId, propertyImageRequest)), HttpStatus.CREATED);
    }

    @DeleteMapping("/property/{propertyId}/images/{imageId}")
    public ResponseEntity<String> deletePropertyImageById(@PathVariable final Long imageId) {
        return new ResponseEntity<>(propertyImageService.softDeletePropertyImage(imageId), HttpStatus.OK);
    }
}