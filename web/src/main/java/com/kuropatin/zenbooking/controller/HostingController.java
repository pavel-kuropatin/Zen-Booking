package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.request.PropertyImageRequest;
import com.kuropatin.zenbooking.model.request.PropertyRequest;
import com.kuropatin.zenbooking.model.response.OrderResponse;
import com.kuropatin.zenbooking.model.response.PropertyImageResponse;
import com.kuropatin.zenbooking.model.response.PropertyResponse;
import com.kuropatin.zenbooking.model.response.SuccessfulResponse;
import com.kuropatin.zenbooking.security.util.AuthenticationUtils;
import com.kuropatin.zenbooking.service.OrderService;
import com.kuropatin.zenbooking.service.PropertyImageService;
import com.kuropatin.zenbooking.service.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/hosting")
@RequiredArgsConstructor
@Api(value = "Hosting Controller", tags = "User Hosting", description = "Actions for managing user property and requests")
public class HostingController {

    private final PropertyService propertyService;
    private final PropertyImageService propertyImageService;
    private final OrderService orderService;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Get all property of logged user")
    @GetMapping("/property")
    public ResponseEntity<List<PropertyResponse>> getAllPropertyOfUser() {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToListPropertyResponse(propertyService.getAllPropertyOfUser(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get property with id {propertyId} of logged user")
    @ApiImplicitParam(name = "propertyId", dataTypeClass = String.class, paramType = "path", value = "propertyId", required = true)
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable final Long propertyId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToNewPropertyResponse(propertyService.getPropertyByIdAndUserId(propertyId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Create property for logged user")
    @PostMapping("/property")
    public ResponseEntity<PropertyResponse> createProperty(@Valid @RequestBody final PropertyRequest propertyRequest) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToNewPropertyResponse(propertyService.createProperty(userId, propertyRequest)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update property with id {propertyId} of logged user")
    @ApiImplicitParam(name = "propertyId", dataTypeClass = String.class, paramType = "path", value = "propertyId", required = true)
    @PutMapping("/property/{propertyId}")
    public ResponseEntity<PropertyResponse> updateProperty(@PathVariable final Long propertyId, @Valid @RequestBody final PropertyRequest propertyRequest) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToNewPropertyResponse(propertyService.updateProperty(propertyId, userId, propertyRequest)), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete property with id {propertyId} of logged user")
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<SuccessfulResponse> deletePropertyById(@PathVariable final Long propertyId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.softDeletePropertyByIdAndUserId(propertyId, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all images of property with id {propertyId} of logged user")
    @GetMapping("/property/{propertyId}/images")
    public ResponseEntity<List<PropertyImageResponse>> getAllImagesOfProperty(@PathVariable final Long propertyId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyImageService.transformToListPropertyImageResponse(propertyImageService.getAllImagesOfPropertyByIdAndUserId(propertyId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get image with id {imageId} of property with id {propertyId} of logged user")
    @GetMapping("/property/{propertyId}/images/{imageId}")
    public ResponseEntity<PropertyImageResponse> getImageOfPropertyById(@PathVariable final Long propertyId, @PathVariable final Long imageId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyImageService.transformToNewPropertyImageResponse(propertyImageService.getImageOfPropertyByIdAndPropertyIdAndUserId(imageId, propertyId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Create image of property with id {propertyId} of logged user")
    @PostMapping("/property/{propertyId}/images")
    public ResponseEntity<PropertyImageResponse> createImageOfProperty(@PathVariable final Long propertyId, @Valid @RequestBody final PropertyImageRequest propertyImageRequest) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyImageService.transformToNewPropertyImageResponse(propertyImageService.create(propertyId, userId, propertyImageRequest)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete image with id {imageId} of property with id {propertyId} of logged user")
    @DeleteMapping("/property/{propertyId}/images/{imageId}")
    public ResponseEntity<SuccessfulResponse> deleteImageOfPropertyById(@PathVariable final Long propertyId, @PathVariable final Long imageId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyImageService.softDeletePropertyImageByIdAndPropertyIdAndUserId(imageId, propertyId, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all order requests of logged user")
    @GetMapping("/requests")
    public ResponseEntity<List<OrderResponse>> getAllOrderRequests() {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.transformToListOrderResponse(orderService.getAllOrderRequestsOfUser(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get order request with id {orderId} of logged user")
    @GetMapping("/requests/{orderId}")
    public ResponseEntity<OrderResponse> getOrderRequestById(@PathVariable final Long orderId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.transformToNewOrderResponse(orderService.getOrderRequestByIdAndUserId(orderId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Accept order request with id {orderId} of logged user")
    @PutMapping("/requests/{orderId}/accept")
    public ResponseEntity<SuccessfulResponse> acceptOrder(@PathVariable final Long orderId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.acceptOrder(orderId, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "Decline order request with id {orderId} of logged user")
    @PutMapping("/requests/{orderId}/decline")
    public ResponseEntity<SuccessfulResponse> declineOrder(@PathVariable final Long orderId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.declineOrder(orderId, userId), HttpStatus.OK);
    }
}