package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.request.ReviewRequest;
import com.kuropatin.zenbooking.model.response.ErrorResponse;
import com.kuropatin.zenbooking.model.response.OrderResponse;
import com.kuropatin.zenbooking.model.response.ReviewResponse;
import com.kuropatin.zenbooking.security.util.AuthenticationUtils;
import com.kuropatin.zenbooking.service.OrderService;
import com.kuropatin.zenbooking.service.ReviewService;
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
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Controller", description = "Actions for user reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final OrderService orderService;
    private final AuthenticationUtils authenticationUtils;

    @Operation(summary = "Get all reviews of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = ReviewResponse.class)))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewResponse>> getAllReviewsOfUser() {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final List<ReviewResponse> response = reviewService.transformToListReviewResponse(reviewService.getAllReviewsOfUser(userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get review of logged user by id {reviewId}", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = ReviewResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "404", description = "Review Not Found", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(path = "/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponse> getReviewOfUserById(
            @PathVariable final Long reviewId
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final ReviewResponse response = reviewService.transformToNewReviewResponse(reviewService.getReviewOfUserById(reviewId, userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get a list of orders to add a review", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping(path = "/available-for-review", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getOrdersToAddReview() {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri);
        final List<OrderResponse> response = orderService.transformToListOrderResponse(orderService.getOrdersToAddReview(userId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Add review", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = ReviewResponse.class))
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
    @PostMapping(path = "/available-for-review/{orderId}/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponse> addReview(
                                                    @PathVariable final Long orderId,
                                                    @Valid @RequestBody final ReviewRequest request
    ) {
        final long userId = authenticationUtils.getId();
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        LogHelper.logRequest(uri, request);
        final ReviewResponse response = reviewService.transformToNewReviewResponse(reviewService.createReview(request, userId, orderId));
        LogHelper.logResponse(uri, response);
        return ResponseEntity.created(uri).body(response);
    }
}