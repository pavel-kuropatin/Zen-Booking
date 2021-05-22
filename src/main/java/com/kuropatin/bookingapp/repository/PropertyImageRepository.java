package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.PropertyImage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropertyImageRepository extends CrudRepository<PropertyImage, Long> {

    @Query(value = "SELECT * FROM property_image WHERE property_id = ?1", nativeQuery = true)
    List<PropertyImage> getAllImagesOfProperty(Long propertyId);

    @Query(value = "SELECT * FROM property_image WHERE property_id = ?1 AND id = ?2", nativeQuery = true)
    PropertyImage getImageOfPropertyById(Long propertyId, Long imageId);
}