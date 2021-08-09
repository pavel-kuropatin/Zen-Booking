package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.util.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface UserRepository extends CrudRepository<User, Long> {

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsByLoginAndIsBannedFalse'+#login")
    boolean existsByLoginAndIsBannedFalse(String login);

    @Cacheable(value = CacheNames.USER, key = "'existsByLoginAndIsBannedFalse'+#id")
    User findUserByIdAndIsBannedFalse(Long id);

    @Cacheable(value = CacheNames.USER, key = "'existsByLoginAndIsBannedFalse'+#login")
    User findUserByLoginAndIsBannedFalse(String login);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsByLoginAndIsBannedFalse'+#login")
    @Query(value = "SELECT CASE WHEN COUNT(u.login) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM User u WHERE u.login = ?1")
    boolean isLoginInUse(String login);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsByLoginAndIsBannedFalse'+#email")
    @Query(value = "SELECT CASE WHEN COUNT(u.email) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM User u WHERE u.email = ?1")
    boolean isEmailInUse(String email);

    @Cacheable(value = CacheNames.BOOLEAN, key = "'existsByLoginAndIsBannedFalse'+#id")
    @Query(value = "SELECT CASE WHEN COUNT(u.id) > 0 THEN TRUE ELSE FALSE END " +
                   "FROM User u WHERE u.isBanned = true AND u.id = ?1")
    boolean isBanned(long id);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE User u SET u.isBanned = true, u.updated = ?2 WHERE u.id = ?1")
    User banUser(Long id, Timestamp updated);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE User u SET u.isBanned = false, u.updated = ?2 WHERE u.id = ?1")
    User unbanUser(Long id, Timestamp updated);
}