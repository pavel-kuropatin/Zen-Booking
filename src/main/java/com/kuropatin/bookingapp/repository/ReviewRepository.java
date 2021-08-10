package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Review;
import com.kuropatin.bookingapp.util.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsReviewByIdAndUserId'+#reviewId+#userId")
    @Query(value = "SELECT CASE WHEN COUNT(p.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id = ?2")
    boolean existsReviewByIdAndUserId(Long reviewId, Long userId);

    @Query(value = "SELECT CASE WHEN COUNT(o.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Order o " +
                   "WHERE o.id = ?1 AND o.user.id = ?2 AND o.isFinished = true AND o.isAccepted = true AND o.isCancelled = false")
    boolean canReviewBeAdded(Long orderId, Long userId);

    @Cacheable(value = CacheNames.DOUBLE, key = "'getRatingOfProperty'+#propertyId")
    @Query(value = "SELECT AVG(r.rating) " +
                   "FROM Review r " +
                   "WHERE r.order.property.id = ?1 AND r.isDeleted = false")
    Optional<Double> getRatingOfProperty(Long propertyId);

    @Cacheable(value = CacheNames.REVIEW, key = "'findAllReviewsOfUser'+#userId")
    @Query(value = "SELECT r " +
                   "FROM Review r " +
                   "WHERE r.isDeleted = false AND r.order.user.id = ?1 " +
                   "ORDER BY r.id DESC")
    List<Review> findAllReviewsOfUser(Long userId);

    @Cacheable(value = CacheNames.REVIEW, key = "'findReviewOfUserById'+#reviewId+#userId")
    @Query(value = "SELECT r " +
                   "FROM Review r " +
                   "WHERE r.isDeleted = false AND r.id = ?1 AND r.order.user.id = ?2 ORDER BY r.id DESC")
    Review findReviewOfUserById(Long reviewId, Long userId);

    @Cacheable(value = CacheNames.REVIEW, key = "'findReviewById'+#reviewId")
    Review findReviewById(Long reviewId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Review r " +
                   "SET r.isDeleted = true, r.updated = ?2 " +
                   "WHERE r.id = ?1")
    void softDeleteReview(Long reviewId, Timestamp updated);
}