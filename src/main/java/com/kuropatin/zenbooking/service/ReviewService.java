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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final OrderService orderService;

    public List<Review> getAllReviewsOfUser(final Long userId) {
        return repository.findAllReviewsOfUser(userId);
    }

    public Review getReviewOfUserById(final Long reviewId, final Long userId) {
        if (repository.existsReviewByIdAndUserId(reviewId, userId)) {
            return repository.findReviewOfUserById(reviewId, userId);
        } else {
            throw new ReviewNotFoundException(reviewId);
        }
    }

    public Review getById(final Long reviewId) {
        return repository.findReviewById(reviewId);
    }

    public Review createReview(final ReviewRequest reviewRequest, final Long userId, final Long orderId) {
        if (repository.canReviewBeAdded(orderId, userId)) {
            Order order = orderService.getOrderById(orderId, userId);
            Review review = transformToNewReview(reviewRequest);
            order.addReview(review);
            review.setOrder(order);
            final Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
            review.setCreated(timestamp);
            review.setUpdated(timestamp);
            return repository.save(review);
        } else {
            throw new ReviewCannotBeAddedException(orderId);
        }
    }

    public SuccessfulResponse softDeleteReview(final Long reviewId) {
        if (repository.existsById(reviewId)) {
            final Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
            repository.softDeleteReview(reviewId, timestamp);
            return new SuccessfulResponse(timestamp, String.format("Review with id: %s successfully deleted", reviewId));
        } else {
            throw new ReviewNotFoundException(reviewId);
        }
    }

    public Review transformToNewReview(final ReviewRequest reviewRequest) {
        return transformToReview(reviewRequest, new Review());
    }

    public Review transformToReview(final ReviewRequest reviewRequest, final Review review) {
        review.setSummary(reviewRequest.getSummary());
        review.setDescription(reviewRequest.getDescription());
        review.setRating(reviewRequest.getRating());
        return review;
    }

    public ReviewResponse transformToNewReviewResponse(final Review review) {
        return transformToReviewResponse(review, new ReviewResponse());
    }

    public List<ReviewResponse> transformToListReviewResponse(final List<Review> reviews) {
        final List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for (final Review review : reviews) {
            reviewResponseList.add(transformToNewReviewResponse(review));
        }
        return reviewResponseList;
    }

    private ReviewResponse transformToReviewResponse(final Review review, final ReviewResponse reviewResponse) {
        reviewResponse.setId(review.getId());
        reviewResponse.setPropertyId(review.getOrder().getProperty().getId());
        reviewResponse.setSummary(review.getSummary());
        reviewResponse.setDescription(review.getDescription());
        reviewResponse.setRating(review.getRating());
        return reviewResponse;
    }
}