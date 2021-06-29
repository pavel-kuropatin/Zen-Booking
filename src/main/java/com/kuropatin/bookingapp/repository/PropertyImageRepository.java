package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.PropertyImage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PropertyImageRepository extends CrudRepository<PropertyImage, Long> {

    @Query(value = "SELECT * FROM property_image WHERE is_deleted = false ORDER BY id", nativeQuery = true)
    List<PropertyImage> findAllImages();

    @Query(value = "SELECT * FROM property_image WHERE is_deleted = false AND property_id = ?1 ORDER BY id", nativeQuery = true)
    List<PropertyImage> findAllPropertyImages(Long propertyId);

    @Query(value = "SELECT * FROM property_image WHERE is_deleted = false AND id = ?1", nativeQuery = true)
    PropertyImage findPropertyImageById(Long imageId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE property_image SET is_deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeletePropertyImage(Long imageId);
}