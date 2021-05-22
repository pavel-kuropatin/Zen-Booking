package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/property")
public class PropertyController {

    private final PropertyService service;

    @Autowired
    public PropertyController(PropertyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Property>> getAll(@PathVariable final Long userId) {
        return new ResponseEntity<>(service.getAllPropertyOfUser(userId), HttpStatus.OK);
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<Property> getById(@PathVariable final Long userId, @PathVariable final Long propertyId) {
        return new ResponseEntity<>(service.getPropertyOfUserById(userId, propertyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Property> create(@PathVariable final Long userId, @RequestBody final Property property) {
        property.setUserId(userId);
        Property propertyToSave = service.create(property);
        return new ResponseEntity<>(propertyToSave, HttpStatus.CREATED);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<Property> update(@PathVariable final Long propertyId, @RequestBody final Property property) {
        return new ResponseEntity<>(service.update(propertyId, property), HttpStatus.CREATED);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Property> deleteById(@PathVariable final Long propertyId) {
        return new ResponseEntity<>(service.deleteById(propertyId), HttpStatus.OK);
    }
}