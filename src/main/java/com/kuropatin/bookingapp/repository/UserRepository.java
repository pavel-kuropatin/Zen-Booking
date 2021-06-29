package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE is_banned = false AND is_deleted = false ORDER BY id", nativeQuery = true)
    List<User> findAllUsers();

    @Query(value = "SELECT * FROM users WHERE is_banned = false AND is_deleted = false AND id = ?1", nativeQuery = true)
    User findUserById(Long id);

    @Query(value = "SELECT * FROM users WHERE is_banned = false AND is_deleted = false AND login = ?1", nativeQuery = true)
    User findUserByLogin(String login);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET is_deleted = true WHERE id = ?1", nativeQuery = true)
    void softDeleteUser(Long id);

    @Query(value = "SELECT EXISTS(SELECT login FROM users WHERE login = ?1)", nativeQuery = true)
    boolean isLoginInUse(String login);

    @Query(value = "SELECT EXISTS(SELECT email FROM users WHERE is_deleted = false AND email = ?1)", nativeQuery = true)
    boolean isEmailInUse(String email);

    @Query(value = "SELECT is_banned FROM users WHERE id = ?1", nativeQuery = true)
    boolean isBanned(long id);

    @Query(value = "SELECT is_deleted FROM users WHERE id = ?1", nativeQuery = true)
    boolean isDeleted(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET is_banned = true WHERE id = ?1", nativeQuery = true)
    User banUser(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET is_banned = false WHERE id = ?1", nativeQuery = true)
    User unbanUser(Long id);
}