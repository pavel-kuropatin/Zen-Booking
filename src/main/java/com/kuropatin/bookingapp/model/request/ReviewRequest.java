package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.model.Review;
import com.kuropatin.bookingapp.validation.ShortInRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewRequest {

    @NotBlank(message = "Enter summary")
    @Size(max = 100, message = "Summary should be between 500 characters or lower")
    private String summary;

    @NotBlank(message = "Enter description")
    @Size(max = 500, message = "Description should be between 500 characters or lower")
    private String description;

    @ShortInRange(min = 1, max = 5, message = "Rating should be between 1 and 10")
    private String rating;

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
}