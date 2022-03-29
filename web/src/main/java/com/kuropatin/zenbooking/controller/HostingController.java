package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.request.PropertyImageRequest;
import com.kuropatin.zenbooking.model.request.PropertyRequest;
import com.kuropatin.zenbooking.model.response.ErrorResponse;
import com.kuropatin.zenbooking.model.response.OrderResponse;
import com.kuropatin.zenbooking.model.response.PropertyImageResponse;
import com.kuropatin.zenbooking.model.response.PropertyResponse;
import com.kuropatin.zenbooking.model.response.SuccessfulResponse;
import com.kuropatin.zenbooking.security.util.AuthenticationUtils;
import com.kuropatin.zenbooking.service.OrderService;
import com.kuropatin.zenbooking.service.PropertyImageService;
import com.kuropatin.zenbooking.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/hosting")
@RequiredArgsConstructor
@Tag(name = "Hosting Controller", description = "Actions for managing user property and requests")
public class HostingController {

    private final PropertyService propertyService;
    private final PropertyImageService propertyImageService;
    private final OrderService orderService;
    private final AuthenticationUtils authenticationUtils;

    @Operation(summary = "Get all property of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = PropertyResponse.class)))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(path = "/property", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PropertyResponse>> getAllPropertyOfUser() {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToListPropertyResponse(propertyService.getAllPropertyOfUser(userId)), HttpStatus.OK);
    }

    @Operation(summary = "Get property with id {propertyId} of logged user", description = "Description")
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
    @GetMapping(path = "/property/{propertyId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable final Long propertyId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToNewPropertyResponse(propertyService.getPropertyByIdAndUserId(propertyId, userId)), HttpStatus.OK);
    }

    @Operation(summary = "Create property for logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = PropertyResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @PostMapping(path = "/property", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponse> createProperty(@Valid @RequestBody final PropertyRequest propertyRequest) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToNewPropertyResponse(propertyService.createProperty(userId, propertyRequest)), HttpStatus.CREATED);
    }

    @Operation(summary = "Update property with id {propertyId} of logged user", description = "Description")
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
    @PutMapping(path = "/property/{propertyId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponse> updateProperty(@PathVariable final Long propertyId, @Valid @RequestBody final PropertyRequest propertyRequest) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.transformToNewPropertyResponse(propertyService.updateProperty(propertyId, userId, propertyRequest)), HttpStatus.OK);
    }

    @Operation(summary = "Delete property with id {propertyId} of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = SuccessfulResponse.class))
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
    @DeleteMapping(path = "/property/{propertyId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> deletePropertyById(@PathVariable final Long propertyId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyService.softDeletePropertyByIdAndUserId(propertyId, userId), HttpStatus.OK);
    }

    @Operation(summary = "Get all images of property with id {propertyId} of logged user", description = "Description")
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
    @GetMapping(path = "/property/{propertyId}/images", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PropertyImageResponse>> getAllImagesOfProperty(@PathVariable final Long propertyId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyImageService.transformToListPropertyImageResponse(propertyImageService.getAllImagesOfPropertyByIdAndUserId(propertyId, userId)), HttpStatus.OK);
    }

    @Operation(summary = "Get image with id {imageId} of property with id {propertyId} of logged user", description = "Description")
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
    @GetMapping(path = "/property/{propertyId}/images/{imageId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyImageResponse> getImageOfPropertyById(@PathVariable final Long propertyId, @PathVariable final Long imageId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyImageService.transformToNewPropertyImageResponse(propertyImageService.getImageOfPropertyByIdAndPropertyIdAndUserId(imageId, propertyId, userId)), HttpStatus.OK);
    }

    @Operation(summary = "Create image of property with id {propertyId} of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = PropertyImageResponse.class))
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
    @PostMapping(path = "/property/{propertyId}/images", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyImageResponse> createImageOfProperty(@PathVariable final Long propertyId, @Valid @RequestBody final PropertyImageRequest propertyImageRequest) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyImageService.transformToNewPropertyImageResponse(propertyImageService.create(propertyId, userId, propertyImageRequest)), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete image with id {imageId} of property with id {propertyId} of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = SuccessfulResponse.class))
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
    @DeleteMapping(path = "/property/{propertyId}/images/{imageId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> deleteImageOfPropertyById(@PathVariable final Long propertyId, @PathVariable final Long imageId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(propertyImageService.softDeletePropertyImageByIdAndPropertyIdAndUserId(imageId, propertyId, userId), HttpStatus.OK);
    }

    @Operation(summary = "Get all order requests of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(path = "/requests", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getAllOrderRequests() {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.transformToListOrderResponse(orderService.getAllOrderRequestsOfUser(userId)), HttpStatus.OK);
    }

    @Operation(summary = "Get order request with id {orderId} of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = OrderResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "404", description = "Order Not Found", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(path = "/requests/{orderId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> getOrderRequestById(@PathVariable final Long orderId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.transformToNewOrderResponse(orderService.getOrderRequestByIdAndUserId(orderId, userId)), HttpStatus.OK);
    }

    @Operation(summary = "Accept order request with id {orderId} of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = SuccessfulResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "404", description = "Order Not Found", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @PutMapping(path = "/requests/{orderId}/accept", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> acceptOrder(@PathVariable final Long orderId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.acceptOrder(orderId, userId), HttpStatus.OK);
    }

    @Operation(summary = "Decline order request with id {orderId} of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = SuccessfulResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "404", description = "Order Not Found", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @PutMapping(path = "/requests/{orderId}/decline", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> declineOrder(@PathVariable final Long orderId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.declineOrder(orderId, userId), HttpStatus.OK);
    }
}