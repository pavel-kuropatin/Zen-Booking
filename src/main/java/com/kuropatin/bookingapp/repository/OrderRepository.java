package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    boolean existsByIdAndUserIdAndIsFinishedFalse(Long orderId, Long userId);

    @Query(value = "SELECT CASE WHEN COUNT(o.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM Order o " +
                   "INNER JOIN Property p on p.id = o.property.id AND o.id = ?1 AND p.user.id = ?2 " +
                   "WHERE o.isFinished = false")
    boolean existsByIdAndHostId(Long orderId, Long hostId);

    @Query(value = "SELECT o FROM Order o WHERE o.isFinished = false AND o.user.id = ?1 ORDER BY o.id")
    List<Order> findAllOrdersOfUser(Long userId);

    @Query(value = "SELECT o FROM Order o WHERE o.isFinished = false AND o.id = ?1")
    Order findOrderById(Long orderId);

    @Query(value = "SELECT o FROM Order o WHERE o.isFinished = false AND o.id = ?1 AND o.user.id = ?2")
    Order findOrderByIdAndUserId(Long orderId, Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE orders SET is_cancelled = true, is_finished = true WHERE id = ?1", nativeQuery = true)
    void cancelOrder(Long orderId);

    @Query(value = "SELECT o.* FROM orders o " +
                   "INNER JOIN property p on p.id = o.property_id AND p.user_id = ?1 " +
                   "WHERE o.is_cancelled = false AND o.is_finished = false " +
                   "ORDER BY o.start_date", nativeQuery = true)
    List<Order> findAllOrderRequests(Long userId);

    @Query(value = "SELECT o.* FROM orders o " +
                   "INNER JOIN property p on p.id = o.property_id AND o.id = ?1 AND p.user_id = ?2 " +
                   "WHERE o.is_cancelled = false AND o.is_finished = false " +
                   "ORDER BY o.start_date", nativeQuery = true)
    Order findOrderRequestByIdAndHostId(Long orderId, Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Order o SET o.isAccepted = true WHERE o.id = ?1")
    void acceptOrder(Long orderId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Order o SET o.isAccepted = false, o.isFinished = true WHERE o.id = ?1")
    void declineOrder(Long orderId);
}