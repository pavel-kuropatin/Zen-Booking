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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Api(value = "Booking Controller", tags = "Booking", description = "Searching and browsing property for order")
public class BookingController {

    private final SearchService searchService;
    private final PropertyService propertyService;
    private final OrderService orderService;
    private final PropertyImageService propertyImageService;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Search property that available for order")
    @ApiImplicitParam(name = "X-Auth-Token", value = "JWT Authentication Token", dataTypeClass = String.class, paramType = "header", required = true)
    @PostMapping
    public ResponseEntity<List<PropertyResponse>> searchProperty(@Valid @RequestBody final PropertySearchCriteria searchCriteria) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToListPropertyResponse(searchService.searchProperty(userId, searchCriteria)), HttpStatus.OK);
    }

    @ApiOperation(value = "Browse found property with id {propertyId}")
    @ApiImplicitParam(name = "X-Auth-Token", value = "JWT Authentication Token", dataTypeClass = String.class, paramType = "header", required = true)
    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable final Long propertyId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToNewPropertyResponse(propertyService.getPropertyById(propertyId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Order found property")
    @ApiImplicitParam(name = "X-Auth-Token", value = "JWT Authentication Token", dataTypeClass = String.class, paramType = "header", required = true)
    @PostMapping("/{propertyId}")
    public ResponseEntity<OrderResponse> orderProperty(@Valid @RequestBody final OrderRequest orderRequest, @PathVariable final Long propertyId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.transformToNewOrderResponse(orderService.createOrder(userId, propertyId, orderRequest)), HttpStatus.OK);
    }

    @ApiOperation(value = "Browse all images of found property")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = String.class, paramType = "path", value = "propertyId", required = true)
    })
    @GetMapping("/{propertyId}/images")
    public ResponseEntity<List<PropertyImageResponse>> getAllImagesOfProperty(@PathVariable final Long propertyId) {
        return new ResponseEntity<>(propertyImageService.transformToListPropertyImageResponse(propertyImageService.getAllImagesOfPropertyById(propertyId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Browse image with id {imageId} of found property")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true),
            @ApiImplicitParam(name = "propertyId", dataTypeClass = String.class, paramType = "path", value = "propertyId", required = true),
            @ApiImplicitParam(name = "imageId", dataTypeClass = String.class, paramType = "path", value = "imageId", required = true)
    })
    @GetMapping("/{propertyId}/images/{imageId}")
    public ResponseEntity<PropertyImageResponse> getImageOfPropertyById(@PathVariable final Long propertyId, @PathVariable final Long imageId) {
        return new ResponseEntity<>(propertyImageService.transformToNewPropertyImageResponse(propertyImageService.getImageOfPropertyByIdAndPropertyId(imageId, propertyId)), HttpStatus.OK);
    }
}