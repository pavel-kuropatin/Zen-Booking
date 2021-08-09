package com.kuropatin.bookingapp.model.response;

import com.kuropatin.bookingapp.model.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewResponse {

    private long id;
    private long propertyId;
    private String summary;
    private String description;
    private byte rating;

    public static ReviewResponse transformToNewReviewResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        transformToReviewResponse(review, reviewResponse);
        return reviewResponse;
    }

    public static List<ReviewResponse> transformToListReviewResponse(List<Review> reviews) {
        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for(Review review : reviews) {
            reviewResponseList.add(transformToNewReviewResponse(review));
        }
        return reviewResponseList;
    }

    private static ReviewResponse transformToReviewResponse(Review review, ReviewResponse reviewResponse) {
        reviewResponse.setId(review.getId());
        reviewResponse.setPropertyId(review.getOrder().getProperty().getId());
        reviewResponse.setSummary(review.getSummary());
        reviewResponse.setDescription(review.getDescription());
        reviewResponse.setRating(review.getRating());
        return reviewResponse;
    }
}