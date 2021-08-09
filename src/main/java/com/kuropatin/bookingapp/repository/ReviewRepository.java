package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {

}