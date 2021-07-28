package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByIdAndIsBannedFalse(Long id);

    User findUserByLoginAndIsBannedFalse(String login);

    @Query(value = "SELECT EXISTS(SELECT login FROM users WHERE login = ?1)", nativeQuery = true)
    boolean isLoginInUse(String login);

    @Query(value = "SELECT EXISTS(SELECT email FROM users WHERE email = ?1)", nativeQuery = true)
    boolean isEmailInUse(String email);

    @Query(value = "SELECT is_banned FROM users WHERE id = ?1", nativeQuery = true)
    boolean isBanned(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET is_banned = true WHERE id = ?1", nativeQuery = true)
    User banUser(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET is_banned = false WHERE id = ?1", nativeQuery = true)
    User unbanUser(Long id);
}