package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Property;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query(value = "SELECT EXISTS(SELECT * FROM property WHERE is_deleted = false AND id = ?1 AND user_id = ?2)", nativeQuery = true)
    boolean existsByIdAndUserId(Long propertyId, Long userId);

    @Query(value = "SELECT * FROM property WHERE is_deleted = false AND user_id = ?1 ORDER BY id", nativeQuery = true)
    List<Property> findAllPropertyOfUser(Long userId);

    @Query(value = "SELECT * FROM property WHERE is_deleted = false AND id = ?1 AND user_id = ?2", nativeQuery = true)
    Property findPropertyByIdAndOwnerId(Long propertyId, Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE property SET is_deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeleteProperty(Long propertyId);

    //Query example
//    @Query(value = "SELECT p.* FROM property p " +
//                   "WHERE p.is_deleted = false " +
//                   "AND p.user_id <> ?1 " +
//                   "AND p.is_available = true " +
//                   "AND p.type = ?2 " +
//                   "AND p.price BETWEEN ?3 AND ?4 " +
//                   "AND p.guests >= ?5 " +
//                   "AND p.rooms >= ?6 " +
//                   "AND p.beds >= ?7 " +
//                   "AND p.has_kitchen = ?8 " +
//                   "AND p.has_washer = ?9 " +
//                   "AND p.has_tv = ?10 " +
//                   "AND p.has_internet = ?11 " +
//                   "AND p.is_pets_allowed = ?12 " +
//                   "AND p.id NOT IN(SELECT DISTINCT o.property_id FROM orders o " +
//                                   "WHERE o.is_accepted = false " +
//                                   "AND o.is_finished = false " +
//                                   "AND (?13 BETWEEN o.start_date AND o.end_date " +
//                                        "OR ?14 BETWEEN o.start_date AND o.end_date " +
//                                        "OR o.start_date BETWEEN ?13 AND ?14 " +
//                                        "OR o.end_date BETWEEN ?13 AND ?14)) " +
//                   "ORDER BY p.price ASC " +
//                   "LIMIT 25",
//                   nativeQuery = true)
//    List<Property> searchProperty(String request);
}