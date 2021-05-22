package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.PropertyImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropertyImageRepository extends CrudRepository<PropertyImage, Long> {

    @Query(value = "SELECT * FROM property_image WHERE approved = true AND deleted = false AND property_id = ?1", nativeQuery = true)
    List<PropertyImage> findAllImagesOfProperty(Long propertyId);

    @Query(value = "SELECT * FROM property_image WHERE approved = true AND deleted = false AND id = ?1", nativeQuery = true)
    PropertyImage findPropertyImageById(Long imageId);

    @Query(value = "UPDATE property_image SET deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeletePropertyImage(Long imageId);

    @Query(value = "SELECT * FROM property_image WHERE approved = false AND deleted = false", nativeQuery = true)
    List<PropertyImage> findAllNotApprovedPropertyImage();

    @Query(value = "SELECT * FROM property_image WHERE approved = false AND deleted = false AND id = ?1", nativeQuery = true)
    PropertyImage findNotApprovedPropertyImageById(Long imageId);

    @Query(value = "UPDATE property_image SET approved = true WHERE id = ?1", nativeQuery = true)
    PropertyImage approvePropertyImage(Long imageId);

    @Query(value = "UPDATE property_image SET approved = false WHERE id = ?1", nativeQuery = true)
    PropertyImage disapprovePropertyImage(Long imageId);
}