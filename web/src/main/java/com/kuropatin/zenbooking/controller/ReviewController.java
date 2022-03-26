package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.request.ReviewRequest;
import com.kuropatin.zenbooking.model.response.ErrorResponse;
import com.kuropatin.zenbooking.model.response.OrderResponse;
import com.kuropatin.zenbooking.model.response.ReviewResponse;
import com.kuropatin.zenbooking.model.response.UserResponse;
import com.kuropatin.zenbooking.security.util.AuthenticationUtils;
import com.kuropatin.zenbooking.service.OrderService;
import com.kuropatin.zenbooking.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/reviews", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Review Controller", description = "Actions for user reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final OrderService orderService;
    private final AuthenticationUtils authenticationUtils;

    @Operation(summary = "Get all reviews of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = UserResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviewsOfUser() {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(reviewService.transformToListReviewResponse(reviewService.getAllReviewsOfUser(userId)), HttpStatus.OK);
    }

    @Operation(summary = "Get review of logged user by id {reviewId}", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = UserResponse.class))
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
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReviewOfUserById(@PathVariable final Long reviewId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(reviewService.transformToNewReviewResponse(reviewService.getReviewOfUserById(reviewId, userId)), HttpStatus.OK);
    }

    @Operation(summary = "Get a list of orders to add a review", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = UserResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping("/available-for-review")
    public ResponseEntity<List<OrderResponse>> getOrdersToAddReview() {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.transformToListOrderResponse(orderService.getOrdersToAddReview(userId)), HttpStatus.OK);
    }

    @Operation(summary = "Add review", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = UserResponse.class))
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
    @PostMapping("/available-for-review/{orderId}/add")
    public ResponseEntity<ReviewResponse> addReview(@Valid @RequestBody final ReviewRequest reviewRequest, @PathVariable final Long orderId) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(reviewService.transformToNewReviewResponse(reviewService.createReview(reviewRequest, userId, orderId)), HttpStatus.CREATED);
    }
}