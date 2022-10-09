package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.model.Order;
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
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsOrderByIdAndUserId'+#orderId+#userId")
    boolean existsByIdAndUserId(final Long orderId, final Long userId);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsOrderByIdAndHostId'+#orderId+#hostId")
    @Query(value = "SELECT CASE WHEN COUNT(o.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Order o " +
                   "INNER JOIN Property p on p.id = o.property.id AND o.id = ?1 AND p.user.id = ?2 " +
                   "WHERE o.status = com.kuropatin.zenbooking.model.OrderStatus.NEW")
    boolean existsByIdAndHostId(final Long orderId, final Long hostId);

    @Cacheable(value = CacheNames.ORDER, key = "'findAllActiveOrdersOfUser'+#userId")
    @Query(value = "SELECT o " +
                   "FROM Order o " +
                   "WHERE o.status IN (com.kuropatin.zenbooking.model.OrderStatus.NEW, com.kuropatin.zenbooking.model.OrderStatus.ACCEPTED) AND o.user.id = ?1 " +
                   "ORDER BY o.id")
    List<Order> findAllActiveOrdersOfUser(final Long userId);

    @Cacheable(value = CacheNames.ORDER, key = "'findAllFinishedOrdersOfUser'+#userId")
    @Query(value = "SELECT o " +
                   "FROM Order o " +
                   "WHERE o.status = com.kuropatin.zenbooking.model.OrderStatus.FINISHED AND o.user.id = ?1 " +
                   "ORDER BY o.id DESC")
    List<Order> findAllFinishedOrdersOfUser(final Long userId);

    @Cacheable(value = CacheNames.ORDER, key = "'findOrdersToAddReview'+#userId")
    @Query(value = "SELECT o " +
                   "FROM Order o " +
                   "WHERE o.status = com.kuropatin.zenbooking.model.OrderStatus.FINISHED AND o.user.id = ?1 " +
                   "AND o.id NOT IN(SELECT r.order.id " +
                                   "FROM Review r " +
                                   "WHERE r.isDeleted = false AND r.order.user.id = ?1)" +
                   "ORDER BY o.id DESC")
    List<Order> findOrdersToAddReview(final Long userId);

    @Cacheable(value = CacheNames.ORDER, key = "'findOrderById'+#orderId")
    Order findOrderById(final Long orderId);

    @Cacheable(value = CacheNames.ORDER, key = "'findOrderByIdAndUserId'+#orderId+#userId")
    @Query(value = "SELECT o " +
                   "FROM Order o " +
                   "WHERE o.id = ?1 AND o.user.id = ?2")
    Order findOrderByIdAndUserId(final Long orderId, final Long userId);

    @Cacheable(value = CacheNames.ORDER, key = "'findAllOrderRequests'+#userId")
    @Query(value = "SELECT o " +
                   "FROM Order o " +
                   "INNER JOIN Property p on p.id = o.property.id AND p.user.id = ?1 " +
                   "WHERE o.status = com.kuropatin.zenbooking.model.OrderStatus.NEW " +
                   "ORDER BY o.startDate")
    List<Order> findAllOrderRequests(final Long userId);

    @Cacheable(value = CacheNames.ORDER, key = "'findOrderRequestByIdAndHostId'+#orderId+#userId")
    @Query(value = "SELECT o " +
                   "FROM Order o " +
                   "INNER JOIN Property p on p.id = o.property.id AND o.id = ?1 AND p.user.id = ?2 " +
                   "WHERE o.status = com.kuropatin.zenbooking.model.OrderStatus.NEW " +
                   "ORDER BY o.startDate")
    Order findOrderRequestByIdAndHostId(final Long orderId, final Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Order o " +
                   "SET o.status = com.kuropatin.zenbooking.model.OrderStatus.CANCELLED, o.updated = ?2 " +
                   "WHERE o.id = ?1")
    void cancelOrder(final Long orderId, final Timestamp updated);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Order o " +
                   "SET o.status = com.kuropatin.zenbooking.model.OrderStatus.ACCEPTED, o.updated = ?2 " +
                   "WHERE o.id = ?1")
    void acceptOrder(final Long orderId, final Timestamp updated);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Order o " +
                   "SET o.status = com.kuropatin.zenbooking.model.OrderStatus.REJECTED, o.updated = ?2 " +
                   "WHERE o.id = ?1")
    void rejectOrder(final Long orderId, final Timestamp updated);

    @Query(value = "SELECT o FROM Order o WHERE o.status = com.kuropatin.zenbooking.model.OrderStatus.NEW")
    List<Order> findOrdersToAutoAccept();

    @Query(value = "SELECT o FROM Order o WHERE o.status = com.kuropatin.zenbooking.model.OrderStatus.ACCEPTED")
    List<Order> findOrdersToAutoFinish();

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Order o " +
                   "SET o.status = com.kuropatin.zenbooking.model.OrderStatus.FINISHED, o.updated = ?2 " +
                   "WHERE o.id = ?1")
    void finishOrder(final Long id, final Timestamp updated);
}