package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.ReviewRequest;
import com.kuropatin.bookingapp.model.response.OrderResponse;
import com.kuropatin.bookingapp.model.response.ReviewResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.OrderService;
import com.kuropatin.bookingapp.service.ReviewService;
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
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Api(value = "Review Controller", tags = "User Review", description = "Actions for user reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final OrderService orderService;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Get all reviews of logged user")
    @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true)
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviewsOfUser() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(ReviewResponse.transformToListReviewResponse(reviewService.getAllReviewsOfUser(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get review of logged user by id {reviewId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true),
            @ApiImplicitParam(name = "reviewId", dataTypeClass = String.class, paramType = "path", value = "orderId", required = true)
    })
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReviewOfUserById(@PathVariable final Long reviewId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(ReviewResponse.transformToNewReviewResponse(reviewService.getReviewOfUserById(reviewId, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get a list of orders to add a review")
    @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true)
    @GetMapping("/available-for-review")
    public ResponseEntity<List<OrderResponse>> getOrdersToAddReview() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(OrderResponse.transformToListOrderResponse(orderService.getOrdersToAddReview(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Add review")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true),
            @ApiImplicitParam(name = "orderId", dataTypeClass = String.class, paramType = "path", value = "orderId", required = true)
    })
    @PostMapping("/available-for-review/{orderId}/add")
    public ResponseEntity<ReviewResponse> addReview(@Valid @RequestBody final ReviewRequest reviewRequest, @PathVariable final Long orderId) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(ReviewResponse.transformToNewReviewResponse(reviewService.createReview(reviewRequest, userId, orderId)), HttpStatus.OK);
    }

    //For moderators
//    @ApiOperation(value = "Delete review with id {reviewId}")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true),
//            @ApiImplicitParam(name = "reviewId", dataTypeClass = String.class, paramType = "path", value = "orderId", required = true)
//    })
//    @DeleteMapping("/{reviewId}")
//    public ResponseEntity<String> deleteReview(@PathVariable final Long reviewId) {
//        return new ResponseEntity<>(reviewService.softDeleteReview(reviewId), HttpStatus.OK);
//    }
}