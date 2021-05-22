package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}