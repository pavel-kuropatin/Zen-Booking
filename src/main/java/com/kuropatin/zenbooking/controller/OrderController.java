package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.response.ErrorResponse;
import com.kuropatin.zenbooking.model.response.OrderResponse;
import com.kuropatin.zenbooking.model.response.SuccessfulResponse;
import com.kuropatin.zenbooking.security.util.AuthenticationUtils;
import com.kuropatin.zenbooking.service.OrderService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "Actions for user orders")
public class OrderController {

    private final OrderService service;
    private final AuthenticationUtils authenticationUtils;

    @Operation(summary = "Get list of all active orders of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = OrderResponse.class))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getAllActive() {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final List<OrderResponse> response = service.transformToListOrderResponse(service.getActiveOrders(userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get list of all finished orders of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(path = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getAllFinished() {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final List<OrderResponse> response = service.transformToListOrderResponse(service.getOrderHistory(userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get order of logged user by order id", description = "Description")
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
    @GetMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> getById(@PathVariable final Long orderId) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final OrderResponse response = service.transformToNewOrderResponse(service.getOrderById(orderId, userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Cancel order of logged user with id {orderId}", description = "Description")
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
    @PutMapping(path = "/{orderId}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> cancel(@PathVariable final Long orderId) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final SuccessfulResponse response = service.cancelOrder(orderId, userId);
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }
}