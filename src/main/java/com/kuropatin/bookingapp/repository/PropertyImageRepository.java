package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.PropertyImage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

public interface PropertyImageRepository extends CrudRepository<PropertyImage, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(i.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM PropertyImage i " +
                   "INNER JOIN Property p ON i.property.id = ?2 AND p.user.id = ?3 " +
                   "WHERE p.isDeleted = false AND i.isDeleted = false AND i.id = ?1")
    boolean existsByIdAndPropertyIdAndUserId(Long imageId, Long propertyId, Long userId);

    @Query(value = "SELECT CASE WHEN COUNT(i.id) > 0 THEN TRUE ELSE FALSE END " +
            "FROM PropertyImage i " +
            "INNER JOIN Property p ON i.property.id = ?2 " +
            "WHERE p.isDeleted = false AND i.isDeleted = false AND i.id = ?1")
    boolean existsByIdAndPropertyId(Long imageId, Long propertyId);

    @Query(value = "SELECT i FROM PropertyImage i " +
                   "INNER JOIN Property p ON i.property.id = p.id AND p.user.id = ?2 " +
                   "WHERE p.isDeleted = false AND i.isDeleted = false AND i.property.id = ?1 " +
                   "ORDER BY i.id")
    List<PropertyImage> findAllImagesOfPropertyOfUser(Long propertyId, Long userId);

    @Query(value = "SELECT i FROM PropertyImage i " +
                   "INNER JOIN Property p ON i.property.id = p.id " +
                   "WHERE p.isDeleted = false AND i.isDeleted = false AND i.property.id = ?1 " +
                   "ORDER BY i.id")
    List<PropertyImage> findAllImagesOfProperty(Long propertyId);

    @Query(value = "SELECT i FROM PropertyImage i " +
                   "INNER JOIN Property p ON i.property.id = ?2 AND p.user.id = ?3 " +
                   "WHERE p.isDeleted = false AND i.isDeleted = false AND i.id = ?1")
    PropertyImage findPropertyImageByIdAndPropertyIdAndUserId(Long imageId, Long propertyId, Long userId);

    @Query(value = "SELECT i FROM PropertyImage i " +
                   "INNER JOIN Property p ON i.property.id = ?2 " +
                   "WHERE p.isDeleted = false AND i.isDeleted = false AND i.id = ?1")
    PropertyImage findPropertyImageByIdAndPropertyId(Long imageId, Long propertyId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE PropertyImage pi SET pi.isDeleted = true WHERE pi.id = ?1")
    void softDeletePropertyImage(Long imageId);
}