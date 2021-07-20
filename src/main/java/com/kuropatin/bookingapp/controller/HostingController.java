package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.PropertyImageRequest;
import com.kuropatin.bookingapp.model.request.PropertyRequest;
import com.kuropatin.bookingapp.model.response.OrderResponse;
import com.kuropatin.bookingapp.model.response.PropertyImageResponse;
import com.kuropatin.bookingapp.model.response.PropertyResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.OrderService;
import com.kuropatin.bookingapp.service.PropertyImageService;
import com.kuropatin.bookingapp.service.PropertyService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hosting")
@RequiredArgsConstructor
public class HostingController {

    private static final String PROPERTY = "/property";
    private static final String PROPERTY_ID = "/property/{propertyId}";
    private static final String PROPERTY_ID_IMAGES = "/property/{propertyId}/images";
    private static final String PROPERTY_ID_IMAGES_ID = "/property/{propertyId}/images/{imageId}";
    private static final String REQUESTS = "/requests";
    private static final String REQUESTS_ID = "/requests/{orderId}";
    private static final String REQUESTS_ID_ACCEPT = "/requests/{orderId}/accept";
    private static final String REQUESTS_ID_DECLINE = "/requests/{orderId}/decline";

    private final PropertyService propertyService;
    private final PropertyImageService propertyImageService;
    private final OrderService orderService;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Get all property of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token")
    })
    @GetMapping(PROPERTY)
    public ResponseEntity<List<PropertyResponse>> getAllPropertyOfUser() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyResponse.transformToListPropertyResponse(propertyService.getAllPropertyOfUser(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get property with id {propertyId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = Integer.class, paramType = "path", value = "propertyId", required = true, defaultValue = "1")
    })
    @GetMapping(PROPERTY_ID)
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable final Long propertyId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyResponse.transformToNewPropertyResponse(propertyService.getPropertyByIdAndUserId(propertyId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Create property for logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token")
    })
    @PostMapping(PROPERTY)
    public ResponseEntity<PropertyResponse> createProperty(@RequestBody final PropertyRequest propertyRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyResponse.transformToNewPropertyResponse(propertyService.createProperty(userId, propertyRequest)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update property with id {propertyId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = Integer.class, paramType = "path", value = "propertyId", required = true, defaultValue = "1")
    })
    @PutMapping(PROPERTY_ID)
    public ResponseEntity<PropertyResponse> updateProperty(@PathVariable final Long propertyId, @RequestBody final PropertyRequest propertyRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyResponse.transformToNewPropertyResponse(propertyService.updateProperty(propertyId, userId, propertyRequest)), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete property with id {propertyId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = Integer.class, paramType = "path", value = "propertyId", required = true, defaultValue = "1")
    })
    @DeleteMapping(PROPERTY_ID)
    public ResponseEntity<String> deletePropertyById(@PathVariable final Long propertyId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.softDeletePropertyByIdAndUserId(propertyId, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all images of property with id {propertyId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = Integer.class, paramType = "path", value = "propertyId", required = true, defaultValue = "1")
    })
    @GetMapping(PROPERTY_ID_IMAGES)
    public ResponseEntity<List<PropertyImageResponse>> getAllImagesOfProperty(@PathVariable final Long propertyId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyImageResponse.transformToListPropertyImageResponse(propertyImageService.getAllImagesOfPropertyByIdAndUserId(propertyId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get image with id {imageId} of property with id {propertyId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = Integer.class, paramType = "path", value = "propertyId", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "imageId", dataTypeClass = Integer.class, paramType = "path", value = "imageId", required = true, defaultValue = "1")
    })
    @GetMapping(PROPERTY_ID_IMAGES_ID)
    public ResponseEntity<PropertyImageResponse> getImageOfPropertyById(@PathVariable final Long propertyId, @PathVariable final Long imageId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyImageResponse.transformToNewPropertyImageResponse(propertyImageService.getImageOfPropertyByIdAndPropertyIdAndUserId(imageId, propertyId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Create image of property with id {propertyId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = Integer.class, paramType = "path", value = "propertyId", required = true, defaultValue = "1")
    })
    @PostMapping(PROPERTY_ID_IMAGES)
    public ResponseEntity<PropertyImageResponse> createImageOfProperty(@PathVariable final Long propertyId, @RequestBody final PropertyImageRequest propertyImageRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyImageResponse.transformToNewPropertyImageResponse(propertyImageService.create(propertyId, userId, propertyImageRequest)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete image with id {imageId} of property with id {propertyId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = Integer.class, paramType = "path", value = "propertyId", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "imageId", dataTypeClass = Integer.class, paramType = "path", value = "imageId", required = true, defaultValue = "1")
    })
    @DeleteMapping(PROPERTY_ID_IMAGES_ID)
    public ResponseEntity<String> deleteImageOfPropertyById(@PathVariable final Long propertyId, @PathVariable final Long imageId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyImageService.softDeletePropertyImageByIdAndPropertyIdAndUserId(imageId, propertyId, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all order requests of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token")
    })
    @GetMapping(REQUESTS)
    public ResponseEntity<List<OrderResponse>> getAllOrderRequests() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToListOrderResponse(orderService.getAllOrderRequestsOfUser(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get order request with id {orderId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "orderId", dataTypeClass = Integer.class, paramType = "path", value = "orderId", required = true, defaultValue = "1")
    })
    @GetMapping(REQUESTS_ID)
    public ResponseEntity<OrderResponse> getOrderRequestById(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToNewOrderResponse(orderService.getOrderRequestByIdAndUserId(orderId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Accept order request with id {orderId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "orderId", dataTypeClass = Integer.class, paramType = "path", value = "orderId", required = true, defaultValue = "1")
    })
    @PutMapping(REQUESTS_ID_ACCEPT)
    public ResponseEntity<String> acceptOrder(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.acceptOrder(orderId, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Decline order request with id {orderId} of logged user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "orderId", dataTypeClass = Integer.class, paramType = "path", value = "orderId", required = true, defaultValue = "1")
    })
    @PutMapping(REQUESTS_ID_DECLINE)
    public ResponseEntity<String> declineOrder(@PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.declineOrder(orderId, userId), HttpStatus.OK);
    }
}