package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Property;
import com.kuropatin.bookingapp.util.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsPropertyByIdAndUserId'+#propertyId+#userId")
    @Query(value = "SELECT CASE WHEN COUNT(p.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id = ?2")
    boolean existsByIdAndUserId(Long propertyId, Long userId);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsPropertyByIdAndNotUserId'+#propertyId+#userId")
    @Query(value = "SELECT CASE WHEN COUNT(p.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id <> ?2")
    boolean existsByIdAndNotUserId(Long propertyId, Long userId);

    @Query(value = "SELECT CASE WHEN COUNT(p.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Property p " +
                   "WHERE p.id NOT IN (SELECT DISTINCT o.property.id " +
                                      "FROM Order o " +
                                      "WHERE o.isFinished = false " +
                                      "AND (?1 BETWEEN o.startDate AND o.endDate " +
                                           "OR ?2 BETWEEN o.startDate AND o.endDate " +
                                           "OR o.startDate BETWEEN ?1 AND ?2 " +
                                           "OR o.endDate BETWEEN ?1 AND ?2))")
    boolean canPropertyBeOrdered(LocalDate startDate, LocalDate endDate);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'findAllPropertyOfUser'+#userId")
    @Query(value = "SELECT p " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.user.id = ?1 " +
                   "ORDER BY p.id")
    List<Property> findAllPropertyOfUser(Long userId);

    @Cacheable(value = CacheNames.PROPERTY, key = "'existsByIdAndNotUserId'+#propertyId+#userId")
    @Query(value = "SELECT p " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id = ?2")
    Property findPropertyByIdAndOwnerId(Long propertyId, Long userId);

    @Cacheable(value = CacheNames.PROPERTY, key = "'findPropertyByIdAndNotOwnerId'+#propertyId+#userId")
    @Query(value = "SELECT p " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id <> ?2")
    Property findPropertyByIdAndNotOwnerId(Long propertyId, Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Property p " +
                   "SET p.isAvailable = false, p.isDeleted = true, p.updated = ?2 " +
                   "WHERE p.id = ?1")
    void softDeleteProperty(Long propertyId, Timestamp updated);
}