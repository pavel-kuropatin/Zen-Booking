package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.model.Property;
import com.kuropatin.zenbooking.util.CacheNames;
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
    boolean existsByIdAndUserId(final Long propertyId, final Long userId);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsPropertyByIdAndNotUserId'+#propertyId+#userId")
    @Query(value = "SELECT CASE WHEN COUNT(p.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id <> ?2")
    boolean existsByIdAndNotUserId(final Long propertyId, final Long userId);

    @Query(value = "SELECT CASE WHEN COUNT(o.id) > 0 THEN FALSE ELSE TRUE END " +
                   "FROM Order o " +
                   "WHERE o.isFinished = false AND o.property.id = ?3 " +
                   "AND (?1 BETWEEN o.startDate AND o.endDate " +
                        "OR ?2 BETWEEN o.startDate AND o.endDate " +
                        "OR o.startDate BETWEEN ?1 AND ?2 " +
                        "OR o.endDate BETWEEN ?1 AND ?2)")
    boolean canPropertyBeOrdered(final LocalDate startDate, final LocalDate endDate, final Long propertyId);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'findAllPropertyOfUser'+#userId")
    @Query(value = "SELECT p " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.user.id = ?1 " +
                   "ORDER BY p.id")
    List<Property> findAllPropertyOfUser(final Long userId);

    @Cacheable(value = CacheNames.PROPERTY, key = "'existsByIdAndNotUserId'+#propertyId+#userId")
    @Query(value = "SELECT p " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id = ?2")
    Property findPropertyByIdAndOwnerId(final Long propertyId, final Long userId);

    @Cacheable(value = CacheNames.PROPERTY, key = "'findPropertyByIdAndNotOwnerId'+#propertyId+#userId")
    @Query(value = "SELECT p " +
                   "FROM Property p " +
                   "WHERE p.isDeleted = false AND p.id = ?1 AND p.user.id <> ?2")
    Property findPropertyByIdAndNotOwnerId(final Long propertyId, final Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Property p " +
                   "SET p.isAvailable = false, p.isDeleted = true, p.updated = ?2 " +
                   "WHERE p.id = ?1")
    void softDeleteProperty(final Long propertyId, final Timestamp updated);
}