package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.ReviewRequest;
import com.kuropatin.bookingapp.model.response.OrderResponse;
import com.kuropatin.bookingapp.model.response.ReviewResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.OrderService;
import com.kuropatin.bookingapp.service.ReviewService;
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
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Api(value = "Review Controller", tags = "User Review", description = "Actions for user reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final OrderService orderService;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Get all reviews of logged user")
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviewsOfUser() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(reviewService.transformToListReviewResponse(reviewService.getAllReviewsOfUser(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get review of logged user by id {reviewId}")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReviewOfUserById(@PathVariable final Long reviewId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(reviewService.transformToNewReviewResponse(reviewService.getReviewOfUserById(reviewId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get a list of orders to add a review")
    @GetMapping("/available-for-review")
    public ResponseEntity<List<OrderResponse>> getOrdersToAddReview() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(orderService.transformToListOrderResponse(orderService.getOrdersToAddReview(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Add review")
    @PostMapping("/available-for-review/{orderId}/add")
    public ResponseEntity<ReviewResponse> addReview(@Valid @RequestBody final ReviewRequest reviewRequest, @PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(reviewService.transformToNewReviewResponse(reviewService.createReview(reviewRequest, userId, orderId)), HttpStatus.CREATED);
    }
}