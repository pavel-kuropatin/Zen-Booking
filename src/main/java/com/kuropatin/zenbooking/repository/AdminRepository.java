package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.model.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    boolean existsByLoginAndIsSuspendedFalseAndIsDeletedFalse(String login);

    Admin findAdminByLoginAndIsSuspendedFalseAndIsDeletedFalse(String login);
}