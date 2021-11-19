package com.kuropatin.zenbooking.service;

import com.kuropatin.zenbooking.exception.UserNotFoundException;
import com.kuropatin.zenbooking.model.Admin;
import com.kuropatin.zenbooking.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;

    public boolean existsByLogin(final String login) {
        return repository.existsByLoginAndIsSuspendedFalseAndIsDeletedFalse(login);
    }

    public Admin getAdminByLogin(final String login) {
        if(existsByLogin(login)) {
            return repository.findAdminByLoginAndIsSuspendedFalseAndIsDeletedFalse(login);
        } else {
            throw new UserNotFoundException(login);
        }
    }
}