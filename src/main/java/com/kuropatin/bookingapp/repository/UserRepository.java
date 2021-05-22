package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE banned = false AND deleted = false", nativeQuery = true)
    List<User> findAllUsers();

    @Query(value = "SELECT * FROM users WHERE banned = false AND deleted = false AND id = ?1", nativeQuery = true)
    User findUserById(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeleteUser(Long id);

    @Query(value = "SELECT * FROM users WHERE banned = true AND deleted = false", nativeQuery = true)
    List<User> findAllBannedUsers();

    @Query(value = "SELECT * FROM users WHERE banned = true AND deleted = false AND id = ?1", nativeQuery = true)
    User findBannedUserById(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET banned = true WHERE id = ?1", nativeQuery = true)
    User banUser(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET banned = false WHERE id = ?1", nativeQuery = true)
    User unbanUser(Long id);
}