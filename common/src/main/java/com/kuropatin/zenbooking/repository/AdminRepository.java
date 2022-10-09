package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.model.Admin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    boolean existsByLoginAndIsSuspendedFalseAndIsDeletedFalse(String login);

    Admin findAdminByLoginAndIsSuspendedFalseAndIsDeletedFalse(String login);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    @Query(value = "UPDATE Admin a " +
            "SET a.lastLogin = ?2 " +
            "WHERE a.id = ?1")
    void updateLastLoginDate(final Long id, final Timestamp updated);
}