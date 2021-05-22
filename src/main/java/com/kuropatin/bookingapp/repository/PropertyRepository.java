package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Property;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query(value = "SELECT * FROM property WHERE approved = true AND banned = false AND deleted = false AND user_id = ?1", nativeQuery = true)
    List<Property> findAllPropertyOfUser(Long userId);

    @Query(value = "SELECT * FROM property WHERE approved = true AND banned = false AND deleted = false AND id = ?1", nativeQuery = true)
    Property findPropertyById(Long propertyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE property SET deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeleteProperty(Long propertyId);

    @Query(value = "SELECT * FROM property WHERE approved = false AND banned = false AND deleted = false", nativeQuery = true)
    List<Property> findAllNotApprovedProperty();

    @Query(value = "SELECT * FROM property WHERE approved = false AND banned = false AND deleted = false AND id = ?1", nativeQuery = true)
    Property findNotApprovedPropertyById(Long propertyId);

    @Query(value = "SELECT * FROM property WHERE banned = true AND deleted = false", nativeQuery = true)
    List<Property> findAllBannedProperty();

    @Query(value = "SELECT * FROM property WHERE banned = true AND deleted = false AND id = ?1", nativeQuery = true)
    Property findBannedPropertyById(Long propertyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE property SET approved = true WHERE id = ?1", nativeQuery = true)
    Property approveProperty(Long propertyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE property SET approved = false WHERE id = ?1", nativeQuery = true)
    Property disapproveProperty(Long propertyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE property SET banned = true WHERE id = ?1", nativeQuery = true)
    Property banProperty(Long propertyId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE property SET banned = false WHERE id = ?1", nativeQuery = true)
    Property unbanProperty(Long propertyId);
}