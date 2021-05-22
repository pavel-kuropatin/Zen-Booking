package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Property;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query(value = "SELECT * FROM property WHERE user_id = ?1", nativeQuery = true)
    List<Property> getAllPropertyOfUser(Long userId);

    @Query(value = "SELECT * FROM property WHERE user_id = ?1 AND id = ?2", nativeQuery = true)
    Property getPropertyOfUserById(Long userId, Long propertyId);
}