package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE is_accepted = true AND is_cancelled = false ORDER BY id", nativeQuery = true)
    List<Order> findAllOrders();

    @Query(value = "SELECT * FROM orders WHERE is_accepted = true AND is_cancelled = false AND id = ?1", nativeQuery = true)
    Order findOrderById(Long orderId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET is_cancelled = true WHERE id = ?1", nativeQuery = true)
    void cancelOrder(Long orderId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET is_accepted = true WHERE id = ?1", nativeQuery = true)
    Order acceptOrder(Long orderId);
}