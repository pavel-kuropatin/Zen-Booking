package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.ReviewCannotBeAddedException;
import com.kuropatin.bookingapp.exception.ReviewNotFoundException;
import com.kuropatin.bookingapp.model.Order;
import com.kuropatin.bookingapp.model.Review;
import com.kuropatin.bookingapp.model.request.ReviewRequest;
import com.kuropatin.bookingapp.model.response.ReviewResponse;
import com.kuropatin.bookingapp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
            order.setReviews(Collections.singleton(review));
            review.setOrder(order);
            review.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            review.setUpdated(review.getCreated());
            return repository.save(review);
        } else {
            throw new ReviewCannotBeAddedException(orderId);
        }
    }

    public String softDeleteReview(Long reviewId) {
        if(repository.existsById(reviewId)) {
            repository.softDeleteReview(reviewId, Timestamp.valueOf(LocalDateTime.now()));
            return MessageFormat.format("Review with id: {0} successfully deleted", reviewId);
        } else {
            throw new ReviewNotFoundException(reviewId);
        }
    }

    public static Review transformToNewReview(ReviewRequest reviewRequest) {
        Review review = new Review();
        transformToReview(reviewRequest, review);
        return review;
    }

    public static Review transformToReview(ReviewRequest reviewRequest, Review review) {
        review.setSummary(reviewRequest.getSummary());
        review.setDescription(reviewRequest.getDescription());
        review.setRating(Byte.parseByte(reviewRequest.getRating()));
        return review;
    }

    public ReviewResponse transformToNewReviewResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        transformToReviewResponse(review, reviewResponse);
        return reviewResponse;
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