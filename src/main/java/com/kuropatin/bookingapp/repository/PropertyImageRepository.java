package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.PropertyImage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PropertyImageRepository extends CrudRepository<PropertyImage, Long> {

    @Query(value = "SELECT EXISTS(SELECT i.* FROM property_image i\n" +
                                 "INNER JOIN property p ON i.property_id = ?3 AND p.user_id = ?2\n" +
                                 "WHERE p.is_deleted = false AND i.is_deleted = false AND i.id = ?1)", nativeQuery = true)
    boolean existsByIdAndPropertyIdAndUserId(Long imageId, Long propertyId, Long userId);

    @Query(value = "SELECT i.* FROM property_image i\n" +
                   "INNER JOIN property p ON i.property_id = p.id AND p.user_id = ?2\n" +
                   "WHERE p.is_deleted = false AND i.is_deleted = false AND i.property_id = ?1\n" +
                   "ORDER BY i.id", nativeQuery = true)
    List<PropertyImage> findAllImagesOfProperty(Long propertyId, Long userId);

    @Query(value = "SELECT i.* FROM property_image i\n" +
                   "INNER JOIN property p ON i.property_id = ?3 AND p.user_id = ?2\n" +
                   "WHERE p.is_deleted = false AND i.is_deleted = false AND i.id = ?1", nativeQuery = true)
    PropertyImage findPropertyImageById(Long imageId, Long propertyId, Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE property_image SET is_deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeletePropertyImage(Long imageId);
}