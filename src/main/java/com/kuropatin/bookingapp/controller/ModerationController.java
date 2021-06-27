package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.model.PropertyImage;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.searchcriteria.PropertyImageSearchCriteria;
import com.kuropatin.bookingapp.model.searchcriteria.PropertySearchCriteria;
import com.kuropatin.bookingapp.model.searchcriteria.UserSearchCriteria;
import com.kuropatin.bookingapp.service.PropertyImageService;
import com.kuropatin.bookingapp.service.PropertyService;
import com.kuropatin.bookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderation")
@RequiredArgsConstructor
public class ModerationController {

    private final UserService userService;
    private final PropertyService propertyService;
    private final PropertyImageService propertyImageService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> searchUsers(@RequestBody UserSearchCriteria userSearchCriteria) {
        return new ResponseEntity<>(userService.searchUsers(userSearchCriteria), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Property>> getAllPropertyOfUser(@PathVariable long userId) {
        return new ResponseEntity<>(propertyService.getAllPropertyOfUser(userId), HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/ban")
    public ResponseEntity<User> banUser(@PathVariable long userId) {
        return new ResponseEntity<>(userService.banUser(userId), HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/unban")
    public ResponseEntity<User> unbanUser(@PathVariable long userId) {
        return new ResponseEntity<>(userService.unbanUser(userId), HttpStatus.OK);
    }

    @GetMapping("/property")
    public ResponseEntity<List<Property>> searchProperty(@RequestBody PropertySearchCriteria propertySearchCriteria) {
        return new ResponseEntity<>(propertyService.searchProperty(propertySearchCriteria), HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<Property> getPropertyById(@PathVariable long propertyId) {
        return new ResponseEntity<>(propertyService.getPropertyById(propertyId), HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<PropertyImage>> getAllImagesOfProperty(@PathVariable long propertyId) {
        return new ResponseEntity<>(propertyImageService.getAllImagesOfProperty(propertyId), HttpStatus.OK);
    }

    @PutMapping("/property/{propertyId}/approve")
    public ResponseEntity<Property> approveProperty(@PathVariable long propertyId) {
        return new ResponseEntity<>(propertyService.approveProperty(propertyId), HttpStatus.OK);
    }

    @PutMapping("/property/{propertyId}/disapprove")
    public ResponseEntity<Property> disapproveProperty(@PathVariable long propertyId) {
        return new ResponseEntity<>(propertyService.disapproveProperty(propertyId), HttpStatus.OK);
    }

    @PutMapping("/property/{propertyId}/ban")
    public ResponseEntity<Property> banProperty(@PathVariable long propertyId) {
        return new ResponseEntity<>(propertyService.banProperty(propertyId), HttpStatus.OK);
    }

    @PutMapping("/property/{propertyId}/unban")
    public ResponseEntity<Property> unbanProperty(@PathVariable long propertyId) {
        return new ResponseEntity<>(propertyService.unbanProperty(propertyId), HttpStatus.OK);
    }

    @GetMapping("/property")
    public ResponseEntity<List<PropertyImage>> searchPropertyImage(@RequestBody PropertyImageSearchCriteria propertyImageSearchCriteria) {
        return new ResponseEntity<>(propertyImageService.searchImages(propertyImageSearchCriteria), HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<PropertyImage> getPropertyImageById(@PathVariable long propertyId) {
        return new ResponseEntity<>(propertyImageService.getPropertyImageById(propertyId), HttpStatus.OK);
    }

    @PutMapping("/property/{propertyId}/approve")
    public ResponseEntity<PropertyImage> approvePropertyImage(@PathVariable long propertyId) {
        return new ResponseEntity<>(propertyImageService.approvePropertyImage(propertyId), HttpStatus.OK);
    }

    @PutMapping("/property/{propertyId}/disapprove")
    public ResponseEntity<PropertyImage> disapprovePropertyImage(@PathVariable long propertyId) {
        return new ResponseEntity<>(propertyImageService.disapprovePropertyImage(propertyId), HttpStatus.OK);
    }
}