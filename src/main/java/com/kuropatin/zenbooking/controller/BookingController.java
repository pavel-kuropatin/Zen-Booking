package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.request.OrderRequest;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import com.kuropatin.zenbooking.model.response.OrderResponse;
import com.kuropatin.zenbooking.model.response.PropertyImageResponse;
import com.kuropatin.zenbooking.model.response.PropertyResponse;
import com.kuropatin.zenbooking.security.util.AuthenticationUtils;
import com.kuropatin.zenbooking.service.OrderService;
import com.kuropatin.zenbooking.service.PropertyImageService;
import com.kuropatin.zenbooking.service.PropertyService;
import com.kuropatin.zenbooking.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping
    public ResponseEntity<List<PropertyResponse>> searchProperty(@Valid @RequestBody final PropertySearchCriteria searchCriteria) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToListPropertyResponse(searchService.searchProperty(userId, searchCriteria)), HttpStatus.OK);
    }

    @ApiOperation(value = "Browse found property with id {propertyId}")
    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable final Long propertyId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToNewPropertyResponse(propertyService.getPropertyById(propertyId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Order found property")
    @PostMapping("/{propertyId}")
    public ResponseEntity<OrderResponse> orderProperty(@Valid @RequestBody final OrderRequest orderRequest, @PathVariable final Long propertyId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.transformToNewOrderResponse(orderService.createOrder(userId, propertyId, orderRequest)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Browse all images of found property")
    @GetMapping("/{propertyId}/images")
    public ResponseEntity<List<PropertyImageResponse>> getAllImagesOfProperty(@PathVariable final Long propertyId) {
        return new ResponseEntity<>(propertyImageService.transformToListPropertyImageResponse(propertyImageService.getAllImagesOfPropertyById(propertyId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Browse image with id {imageId} of found property")
    @GetMapping("/{propertyId}/images/{imageId}")
    public ResponseEntity<PropertyImageResponse> getImageOfPropertyById(@PathVariable final Long propertyId, @PathVariable final Long imageId) {
        return new ResponseEntity<>(propertyImageService.transformToNewPropertyImageResponse(propertyImageService.getImageOfPropertyByIdAndPropertyId(imageId, propertyId)), HttpStatus.OK);
    }
}