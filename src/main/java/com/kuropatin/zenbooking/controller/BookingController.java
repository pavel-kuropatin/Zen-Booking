package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.request.OrderRequest;
import com.kuropatin.zenbooking.model.request.PropertySearchCriteria;
import com.kuropatin.zenbooking.model.response.ErrorResponse;
import com.kuropatin.zenbooking.model.response.OrderResponse;
import com.kuropatin.zenbooking.model.response.PropertyImageResponse;
import com.kuropatin.zenbooking.model.response.PropertyResponse;
import com.kuropatin.zenbooking.security.util.AuthenticationUtils;
import com.kuropatin.zenbooking.service.OrderService;
import com.kuropatin.zenbooking.service.PropertyImageService;
import com.kuropatin.zenbooking.service.PropertyService;
import com.kuropatin.zenbooking.service.SearchService;
import com.kuropatin.zenbooking.util.LogHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
@Tag(name = "Booking Controller", description = "Searching and browsing property for order")
public class BookingController {

    private final SearchService searchService;
    private final PropertyService propertyService;
    private final OrderService orderService;
    private final PropertyImageService propertyImageService;
    private final AuthenticationUtils authenticationUtils;

    @Operation(summary = "Search property that available for order", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = PropertyResponse.class)))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PropertyResponse>> searchProperty(
            @Valid @RequestBody final PropertySearchCriteria request
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri, request);
        final List<PropertyResponse> response = propertyService.transformToListPropertyResponse(searchService.searchProperty(userId, request));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Browse found property with id {propertyId}", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = PropertyResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "404", description = "Property Not Found", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(path = "/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponse> getPropertyById(
            @PathVariable final Long propertyId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final PropertyResponse response = propertyService.transformToNewPropertyResponse(propertyService.getPropertyById(propertyId, userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Order found property", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = OrderResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "404", description = "Property Not Found", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @PostMapping(path = "/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> orderProperty(
            @PathVariable final Long propertyId,
            @Valid @RequestBody final OrderRequest request
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri, request);
        final OrderResponse response = orderService.transformToNewOrderResponse(orderService.createOrder(userId, propertyId, request));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Browse all images of found property", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = PropertyImageResponse.class)))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "404", description = "Property Not Found", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(path = "/{propertyId}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PropertyImageResponse>> getAllImagesOfProperty(
            @PathVariable final Long propertyId
    ) {
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final List<PropertyImageResponse> response = propertyImageService.transformToListPropertyImageResponse(propertyImageService.getAllImagesOfPropertyById(propertyId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Browse image with id {imageId} of found property", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = PropertyImageResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "404", description = "Property Not Found", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "404", description = "Image Not Found", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(path = "/{propertyId}/images/{imageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyImageResponse> getImageOfPropertyById(
            @PathVariable final Long propertyId,
            @PathVariable final Long imageId) {
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final PropertyImageResponse response = propertyImageService.transformToNewPropertyImageResponse(propertyImageService.getImageOfPropertyByIdAndPropertyId(imageId, propertyId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }
}