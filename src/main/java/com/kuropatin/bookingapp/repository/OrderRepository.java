package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE accepted_by_host = true AND cancelled = false AND property_id = ?1", nativeQuery = true)
    List<Order> findAllOrders(Long propertyId);

    @Query(value = "SELECT * FROM orders WHERE accepted_by_host = true AND cancelled = false AND id = ?1", nativeQuery = true)
    Order findOrderById(Long orderId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET cancelled = true WHERE id = ?1", nativeQuery = true)
    void cancelOrder(Long orderId);

    @Query(value = "SELECT * FROM orders WHERE accepted_by_host = false AND cancelled = false", nativeQuery = true)
    List<Order> findAllNotApprovedPOrders();

    @Query(value = "SELECT * FROM orders WHERE accepted_by_host = false AND cancelled = false AND id = ?1", nativeQuery = true)
    Order findNotApprovedOrdersById(Long orderId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET accepted_by_host = true WHERE id = ?1", nativeQuery = true)
    Order acceptOrder(Long orderId);
}