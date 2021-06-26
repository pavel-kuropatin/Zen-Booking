package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.InsufficientMoneyAmountException;
import com.kuropatin.bookingapp.exception.UserNotFoundException;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.request.AmountRequest;
import com.kuropatin.bookingapp.model.request.UserRequest;
import com.kuropatin.bookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAllUsers();
    }

    public User getUserById(Long id) {
        if(repository.existsById(id)) {
            return repository.findUserById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public User getUserByLogin(String login) {
        User user = repository.findUserByLogin(login);
        if(user != null) {
            return user;
        } else {
            throw new UserNotFoundException(login);
        }
    }

    public User createUser(UserRequest userRequest) {
        User user = UserRequest.transformToNewUser(userRequest);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdated(user.getCreated());
        return repository.save(user);
    }

    public User updateUser(Long id, UserRequest userRequest) {
        User userToUpdate = getUserById(id);
        UserRequest.transformToUser(userRequest, userToUpdate);
        userToUpdate.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(userToUpdate);
    }

    public User deposit(Long id, AmountRequest amountRequest) {
        User user = getUserById(id);
        user.setBalance(user.getBalance() + amountRequest.getAmount());
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(user);
    }

    public void pay(User user, int amount) {
        if(user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            repository.save(user);
        } else {
            throw new InsufficientMoneyAmountException();
        }
    }

    public void payBack(User user, int amount) {
        user.setBalance(user.getBalance() + amount);
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(user);
    }

    public String softDeleteUser(Long id) {
        if(repository.existsById(id)) {
            repository.softDeleteUser(id);
            return MessageFormat.format("User with id: {0} successfully deleted", id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public List<User> findAllBannedUsers(){
        return repository.findAllBannedUsers();
    }

    public User findBannedUserById(Long id){
        if(repository.existsById(id)) {
            return repository.findBannedUserById(id);
        } else {
            throw new UserNotFoundException(id);
        }
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