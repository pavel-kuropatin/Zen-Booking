package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.EmailAlreadyInUseException;
import com.kuropatin.bookingapp.exception.InsufficientMoneyAmountException;
import com.kuropatin.bookingapp.exception.LoginAlreadyInUseException;
import com.kuropatin.bookingapp.exception.UserNotFoundException;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.request.AmountRequest;
import com.kuropatin.bookingapp.model.request.UserRequest;
import com.kuropatin.bookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getUserById(Long id) {
        if(repository.existsById(id)) {
            return repository.findUserByIdAndIsBannedFalseAndIsDeletedFalse(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public User getUserByLogin(String login) {
        User user = repository.findUserByLoginAndIsBannedFalseAndIsDeletedFalse(login);
        if(user != null) {
            return user;
        } else {
            throw new UserNotFoundException(login);
        }
    }

    public User createUser(UserRequest userRequest) {
        if(repository.isLoginInUse(userRequest.getLogin())) {
            throw new LoginAlreadyInUseException(userRequest.getLogin());
        }
        if(repository.isEmailInUse(userRequest.getEmail())) {
            throw new EmailAlreadyInUseException(userRequest.getEmail());
        }
        User user = UserRequest.transformToNewUser(userRequest);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdated(user.getCreated());
        return repository.save(user);
    }

    public User updateUser(Long id, UserRequest userRequest) {
        if(repository.isLoginInUse(userRequest.getLogin())) {
            throw new LoginAlreadyInUseException(userRequest.getLogin());
        }
        if(repository.isEmailInUse(userRequest.getEmail())) {
            throw new EmailAlreadyInUseException(userRequest.getEmail());
        }
        User userToUpdate = getUserById(id);
        UserRequest.transformToUser(userRequest, userToUpdate);
        userToUpdate.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(userToUpdate);
    }

    public User deposit(Long id, AmountRequest amountRequest) {
        if(repository.isBanned(id) || repository.isDeleted(id)) {
            throw new UserNotFoundException(id);
        }
        User user = getUserById(id);
        user.setBalance(user.getBalance() + amountRequest.getAmount());
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(user);
    }

    @Transactional
    public void pay(User user, int amount) {
        if(user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            repository.save(user);
        } else {
            throw new InsufficientMoneyAmountException();
        }
    }

    public void transferMoney(User user, int amount) {
        user.setBalance(user.getBalance() + amount);
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(user);
    }

    public User banUser(Long id) {
        if(repository.existsById(id)) {
            return repository.banUser(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public User unbanUser(Long id) {
        if(repository.existsById(id)) {
            return repository.unbanUser(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }
}