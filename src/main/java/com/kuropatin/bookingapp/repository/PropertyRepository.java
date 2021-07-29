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

    @Query(value = "SELECT p FROM Property p WHERE p.isDeleted = false AND p.user.id = ?1 ORDER BY p.id")
    List<Property> findAllPropertyOfUser(Long userId);

    @Query(value = "SELECT p FROM Property p WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id = ?2")
    Property findPropertyByIdAndOwnerId(Long propertyId, Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE property SET is_deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeleteProperty(Long propertyId);

    //Query example
//    @Query(value = "SELECT p FROM Property p " +
//                   "WHERE p.isDeleted = false " +
//                   "AND p.user.id <> ?1 " +
//                   "AND p.isAvailable = true " +
//                   "AND p.type = ?2 " +
//                   "AND LOWER(p.address) LIKE LOWER(CONCAT('%', ?3,'%')) " +
//                   "AND p.price BETWEEN ?4 AND ?5 " +
//                   "AND p.guests >= ?6 " +
//                   "AND p.rooms >= ?7 " +
//                   "AND p.beds >= ?8 " +
//                   "AND p.hasKitchen = ?9 " +
//                   "AND p.hasWasher = ?10 " +
//                   "AND p.hasTv = ?11 " +
//                   "AND p.hasInternet = ?12 " +
//                   "AND p.isPetsAllowed = ?13 " +
//                   "AND p.id NOT IN(" +
//                       "SELECT DISTINCT o.property.id FROM Order o " +
//                       "WHERE o.isAccepted = false " +
//                       "AND o.isFinished = false " +
//                       "AND (" +
//                           "?14 BETWEEN o.startDate AND o.endDate " +
//                           "OR ?15 BETWEEN o.startDate AND o.endDate " +
//                           "OR o.startDate BETWEEN ?14 AND ?15 " +
//                           "OR o.endDate BETWEEN ?14 AND ?15)" +
//                       ") " +
//                   "ORDER BY p.price ASC"
//    )
//    List<Property> searchProperty(String request);
}