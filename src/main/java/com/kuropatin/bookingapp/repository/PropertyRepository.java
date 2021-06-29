package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Property;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query(value = "SELECT * FROM property WHERE is_deleted = false ORDER BY id", nativeQuery = true)
    List<Property> findAllProperty();

    @Query(value = "SELECT * FROM property WHERE is_deleted = false AND user_id = ?1 ORDER BY id", nativeQuery = true)
    List<Property> findAllPropertyOfUser(Long userId);

    @Query(value = "SELECT * FROM property WHERE is_deleted = false AND id = ?1", nativeQuery = true)
    Property findPropertyById(Long propertyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE property SET is_deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeleteProperty(Long propertyId);
}