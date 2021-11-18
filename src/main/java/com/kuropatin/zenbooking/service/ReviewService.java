package com.kuropatin.zenbooking.service;

import com.kuropatin.zenbooking.exception.ReviewCannotBeAddedException;
import com.kuropatin.zenbooking.exception.ReviewNotFoundException;
import com.kuropatin.zenbooking.model.Order;
import com.kuropatin.zenbooking.model.Review;
import com.kuropatin.zenbooking.model.request.ReviewRequest;
import com.kuropatin.zenbooking.model.response.ReviewResponse;
import com.kuropatin.zenbooking.model.response.SuccessfulResponse;
import com.kuropatin.zenbooking.repository.ReviewRepository;
import com.kuropatin.zenbooking.util.ApplicationTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final OrderService orderService;

    public List<Review> getAllReviewsOfUser(Long userId) {
        return repository.findAllReviewsOfUser(userId);
    }

    public Review getReviewOfUserById(Long reviewId, Long userId) {
        if(repository.existsReviewByIdAndUserId(reviewId, userId)) {
            return repository.findReviewOfUserById(reviewId, userId);
        } else {
            throw new ReviewNotFoundException(reviewId);
        }
    }

    public Review getById(Long reviewId) {
        return repository.findReviewById(reviewId);
    }

    public Review createReview(ReviewRequest reviewRequest, Long userId, Long orderId) {
        if(repository.canReviewBeAdded(orderId, userId)) {
            Order order = orderService.getOrderById(orderId, userId);
            Review review = transformToNewReview(reviewRequest);
            order.addReview(review);
            review.setOrder(order);
            Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
            review.setCreated(timestamp);
            review.setUpdated(timestamp);
            return repository.save(review);
        } else {
            throw new ReviewCannotBeAddedException(orderId);
        }
    }

    public SuccessfulResponse softDeleteReview(Long reviewId) {
        if(repository.existsById(reviewId)) {
            Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
            repository.softDeleteReview(reviewId, timestamp);
            return new SuccessfulResponse(timestamp, MessageFormat.format("Review with id: {0} successfully deleted", reviewId));
        } else {
            throw new ReviewNotFoundException(reviewId);
        }
    }

    public Review transformToNewReview(ReviewRequest reviewRequest) {
        return transformToReview(reviewRequest, new Review());
    }

    public Review transformToReview(ReviewRequest reviewRequest, Review review) {
        review.setSummary(reviewRequest.getSummary());
        review.setDescription(reviewRequest.getDescription());
        review.setRating(Byte.parseByte(reviewRequest.getRating()));
        return review;
    }

    public ReviewResponse transformToNewReviewResponse(Review review) {
        return transformToReviewResponse(review, new ReviewResponse());
    }

    public List<ReviewResponse> transformToListReviewResponse(List<Review> reviews) {
        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for(Review review : reviews) {
            reviewResponseList.add(transformToNewReviewResponse(review));
        }
        return reviewResponseList;
    }

    private ReviewResponse transformToReviewResponse(Review review, ReviewResponse reviewResponse) {
        reviewResponse.setId(review.getId());
        reviewResponse.setPropertyId(review.getOrder().getProperty().getId());
        reviewResponse.setSummary(review.getSummary());
        reviewResponse.setDescription(review.getDescription());
        reviewResponse.setRating(review.getRating());
        return reviewResponse;
    }
}