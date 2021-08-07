package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.UserNotFoundException;
import com.kuropatin.bookingapp.model.Admin;
import com.kuropatin.bookingapp.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;

    public boolean existsByLogin(String login) {
        return repository.existsByLoginAndIsSuspendedFalseAndIsDeletedFalse(login);
    }

    public Admin getAdminByLogin(String login) {
        if(existsByLogin(login)) {
            return repository.findAdminByLoginAndIsSuspendedFalseAndIsDeletedFalse(login);
        } else {
            throw new UserNotFoundException(login);
        }
    }
}