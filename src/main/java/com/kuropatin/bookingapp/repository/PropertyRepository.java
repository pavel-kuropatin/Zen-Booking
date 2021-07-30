package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Property;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(p.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Property p WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id = ?2")
    boolean existsByIdAndUserId(Long propertyId, Long userId);

    @Query(value = "SELECT CASE WHEN COUNT(p.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Property p WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id <> ?2")
    boolean existsByIdAndNotUserId(Long propertyId, Long userId);

    @Query(value = "SELECT p FROM Property p WHERE p.isDeleted = false AND p.user.id = ?1 ORDER BY p.id")
    List<Property> findAllPropertyOfUser(Long userId);

    @Query(value = "SELECT p FROM Property p WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id = ?2")
    Property findPropertyByIdAndOwnerId(Long propertyId, Long userId);

    @Query(value = "SELECT p FROM Property p WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id <> ?2")
    Property findPropertyByIdAndNotOwnerId(Long propertyId, Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE property SET is_available = false, is_deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeleteProperty(Long propertyId);
}