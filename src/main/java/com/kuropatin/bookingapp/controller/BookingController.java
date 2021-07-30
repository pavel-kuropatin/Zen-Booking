package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.OrderRequest;
import com.kuropatin.bookingapp.model.request.PropertySearchCriteria;
import com.kuropatin.bookingapp.model.response.OrderResponse;
import com.kuropatin.bookingapp.model.response.PropertyImageResponse;
import com.kuropatin.bookingapp.model.response.PropertyResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.OrderService;
import com.kuropatin.bookingapp.service.PropertyImageService;
import com.kuropatin.bookingapp.service.PropertyService;
import com.kuropatin.bookingapp.service.SearchService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final SearchService searchService;
    private final PropertyService propertyService;
    private final OrderService orderService;
    private final PropertyImageService propertyImageService;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Search property that available to order")
    @ApiImplicitParam(name = "X-Auth-Token", value = "JWT Authentication Token", dataTypeClass = String.class, paramType = "header")
    @PostMapping("/booking")
    public ResponseEntity<List<PropertyResponse>> searchProperty(@RequestBody final PropertySearchCriteria searchCriteria) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyResponse.transformToListPropertyResponse(searchService.searchProperty(userId, searchCriteria)), HttpStatus.OK);
    }

    @ApiOperation(value = "Show founded property by {propertyId}")
    @ApiImplicitParam(name = "X-Auth-Token", value = "JWT Authentication Token", dataTypeClass = String.class, paramType = "header")
    @GetMapping("/booking/{propertyId}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable final Long propertyId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(PropertyResponse.transformToNewPropertyResponse(propertyService.getPropertyById(propertyId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Order founded property")
    @ApiImplicitParam(name = "X-Auth-Token", value = "JWT Authentication Token", dataTypeClass = String.class, paramType = "header")
    @PostMapping("/booking/{propertyId}")
    public ResponseEntity<OrderResponse> orderProperty(@RequestBody final OrderRequest orderRequest, @PathVariable final Long propertyId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToNewOrderResponse(orderService.createOrder(userId, propertyId, orderRequest)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all images of founded property")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = Integer.class, paramType = "path", value = "propertyId", required = true, defaultValue = "1")
    })
    @GetMapping("/booking/{propertyId}/images")
    public ResponseEntity<List<PropertyImageResponse>> getAllImagesOfProperty(@PathVariable final Long propertyId) {
        return new ResponseEntity<>(PropertyImageResponse.transformToListPropertyImageResponse(propertyImageService.getAllImagesOfPropertyById(propertyId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get image with id {imageId} of founded property")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token"),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = Integer.class, paramType = "path", value = "propertyId", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "imageId", dataTypeClass = Integer.class, paramType = "path", value = "imageId", required = true, defaultValue = "1")
    })
    @GetMapping("/booking/{propertyId}/images/{imageId}")
    public ResponseEntity<PropertyImageResponse> getImageOfPropertyById(@PathVariable final Long propertyId, @PathVariable final Long imageId) {
        return new ResponseEntity<>(PropertyImageResponse.transformToNewPropertyImageResponse(propertyImageService.getImageOfPropertyByIdAndPropertyId(imageId, propertyId)), HttpStatus.OK);
    }
}