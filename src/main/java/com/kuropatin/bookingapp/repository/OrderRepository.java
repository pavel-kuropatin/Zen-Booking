package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query(value = "SELECT EXISTS(SELECT * FROM orders WHERE id = ?1 AND user_id = ?2 AND is_finished = false)", nativeQuery = true)
    boolean existsByIdAndUserId(Long orderId, Long userId);

    @Query(value = "SELECT EXISTS(SELECT o.* FROM orders o " +
                                 "INNER JOIN property p on p.id = o.property_id AND o.id = ?1 AND p.user_id = ?2 " +
                                 "WHERE o.is_finished = false)", nativeQuery = true)
    boolean existsByIdAndHostId(Long orderId, Long hostId);

    @Query(value = "SELECT * FROM orders WHERE is_finished = false AND user_id = ?1 ORDER BY id", nativeQuery = true)
    List<Order> findAllOrdersOfUser(Long userId);

    @Query(value = "SELECT * FROM orders WHERE is_finished = false AND id = ?1", nativeQuery = true)
    Order findOrderById(Long orderId);

    @Query(value = "SELECT * FROM orders WHERE is_finished = false AND id = ?1 AND user_id = ?2", nativeQuery = true)
    Order findOrderByIdAndUserId(Long orderId, Long userId);

    @Modifying
    @Transactional
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
    @Transactional
    @Query(value = "UPDATE orders SET is_accepted = true WHERE id = ?1", nativeQuery = true)
    void acceptOrder(Long orderId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET is_accepted = false, is_finished = true WHERE id = ?1", nativeQuery = true)
    void declineOrder(Long orderId);
}