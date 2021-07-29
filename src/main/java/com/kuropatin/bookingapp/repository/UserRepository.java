package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByIdAndIsBannedFalse(Long id);

    User findUserByLoginAndIsBannedFalse(String login);

    @Query(value = "SELECT CASE WHEN COUNT(u.login) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM User u WHERE u.login = ?1")
    boolean isLoginInUse(String login);

    @Query(value = "SELECT CASE WHEN COUNT(u.email) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM User u WHERE u.login = ?1")
    boolean isEmailInUse(String email);

    @Query(value = "SELECT u.isBanned FROM User u WHERE u.id = ?1")
    boolean isBanned(long id);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE User u SET u.isBanned = true WHERE u.id = ?1")
    User banUser(Long id);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE User u SET u.isBanned = false WHERE u.id = ?1")
    User unbanUser(Long id);
}