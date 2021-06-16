package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.PropertyImage;
import com.kuropatin.bookingapp.model.dto.PropertyImageDto;
import com.kuropatin.bookingapp.service.PropertyImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/property/{propertyId}/image")
@RequiredArgsConstructor
public class PropertyImageController {

    private final PropertyImageService service;

    @GetMapping
    public ResponseEntity<List<PropertyImage>> getAll(@PathVariable final Long propertyId) {
        return new ResponseEntity<>(service.getAllImagesOfProperty(propertyId), HttpStatus.OK);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<PropertyImage> getById(@PathVariable final Long imageId) {
        return new ResponseEntity<>(service.getPropertyImageById(imageId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PropertyImage> create(@PathVariable final Long propertyId, @RequestBody final PropertyImageDto propertyImageDto) {
        return new ResponseEntity<>(service.create(propertyId, propertyImageDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteById(@PathVariable final Long imageId) {
        return new ResponseEntity<>(service.softDeletePropertyImage(imageId), HttpStatus.OK);
    }
}