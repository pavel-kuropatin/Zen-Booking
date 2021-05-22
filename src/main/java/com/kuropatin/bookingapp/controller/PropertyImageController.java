package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.PropertyImage;
import com.kuropatin.bookingapp.service.PropertyImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/property/{propertyId}/image")
public class PropertyImageController {

    private final PropertyImageService service;

    @Autowired
    public PropertyImageController(PropertyImageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PropertyImage>> getAll(@PathVariable final Long propertyId) {
        return new ResponseEntity<>(service.getAllImagesOfProperty(propertyId), HttpStatus.OK);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<PropertyImage> getById(@PathVariable final Long imageId) {
        return new ResponseEntity<>(service.getPropertyImageById(imageId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PropertyImage> create(@PathVariable final Long propertyId, @RequestBody final PropertyImage propertyImage) {
        return new ResponseEntity<>(service.create(propertyId, propertyImage), HttpStatus.CREATED);
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<PropertyImage> update(@PathVariable final Long imageId, @RequestBody final PropertyImage propertyImage) {
        return new ResponseEntity<>(service.update(imageId, propertyImage), HttpStatus.CREATED);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteById(@PathVariable final Long imageId) {
        return new ResponseEntity<>(service.softDeletePropertyImage(imageId), HttpStatus.OK);
    }
}