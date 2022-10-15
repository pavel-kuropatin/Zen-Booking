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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
    @GetMapping(path = "/property", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PropertyResponse>> getAllPropertyOfUser() {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final List<PropertyResponse> response = propertyService.transformToListPropertyResponse(propertyService.getAllPropertyOfUser(userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @GetMapping(path = "/property/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponse> getPropertyById(
            @PathVariable final Long propertyId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final PropertyResponse response = propertyService.transformToNewPropertyResponse(propertyService.getPropertyByIdAndUserId(propertyId, userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @PostMapping(path = "/property", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponse> createProperty(
            @Valid @RequestBody final PropertyRequest request
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri, request);
        final PropertyResponse response = propertyService.transformToNewPropertyResponse(propertyService.createProperty(userId, request));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.created(uri).body(response);
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
    @PutMapping(path = "/property/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponse> updateProperty(
            @PathVariable final Long propertyId,
            @Valid @RequestBody final PropertyRequest request
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri, request);
        final PropertyResponse response = propertyService.transformToNewPropertyResponse(propertyService.updateProperty(propertyId, userId, request));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @DeleteMapping(path = "/property/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> deletePropertyById(
            @PathVariable final Long propertyId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final SuccessfulResponse response = propertyService.softDeletePropertyByIdAndUserId(propertyId, userId);
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @GetMapping(path = "/property/{propertyId}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PropertyImageResponse>> getAllImagesOfProperty(
            @PathVariable final Long propertyId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final List<PropertyImageResponse> response = propertyImageService.transformToListPropertyImageResponse(propertyImageService.getAllImagesOfPropertyByIdAndUserId(propertyId, userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @GetMapping(path = "/property/{propertyId}/images/{imageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyImageResponse> getImageOfPropertyById(
            @PathVariable final Long propertyId,
            @PathVariable final Long imageId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final PropertyImageResponse response = propertyImageService.transformToNewPropertyImageResponse(propertyImageService.getImageOfPropertyByIdAndPropertyIdAndUserId(imageId, propertyId, userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @PostMapping(path = "/property/{propertyId}/images", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyImageResponse> createImageOfProperty(
            @PathVariable final Long propertyId,
            @Valid @RequestBody final PropertyImageRequest request
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri, request);
        final PropertyImageResponse response = propertyImageService.transformToNewPropertyImageResponse(propertyImageService.create(propertyId, userId, request));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.created(uri).body(response);
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
    @DeleteMapping(path = "/property/{propertyId}/images/{imageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> deleteImageOfPropertyById(
            @PathVariable final Long propertyId,
            @PathVariable final Long imageId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final SuccessfulResponse response = propertyImageService.softDeletePropertyImageByIdAndPropertyIdAndUserId(imageId, propertyId, userId);
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @GetMapping(path = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getAllOrderRequests() {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final List<OrderResponse> response = orderService.transformToListOrderResponse(orderService.getAllOrderRequestsOfUser(userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @GetMapping(path = "/requests/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> getOrderRequestById(
            @PathVariable final Long orderId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final OrderResponse response = orderService.transformToNewOrderResponse(orderService.getOrderRequestByIdAndUserId(orderId, userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @PutMapping(path = "/requests/{orderId}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> acceptOrder(
            @PathVariable final Long orderId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final SuccessfulResponse response = orderService.acceptOrder(orderId, userId);
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
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
    @PutMapping(path = "/requests/{orderId}/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> rejectOrder(
            @PathVariable final Long orderId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final SuccessfulResponse response = orderService.rejectOrder(orderId, userId);
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }
}