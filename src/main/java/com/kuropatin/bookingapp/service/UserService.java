package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.UserNotFoundException;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;

@Repository
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

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

    public User createUser(User user) {
        return repository.save(user);
    }

    public User updateUser(Long id, User user) {
        User userToUpdate = getUserById(id);
        if(user.equals(userToUpdate)) {
            return userToUpdate;
        } else {
            userToUpdate.setLogin(user.getLogin());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userToUpdate.setBirthDate(user.getBirthDate());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPhone(user.getPhone());
            userToUpdate.setPersonalAccount(user.getPersonalAccount());
            return repository.save(userToUpdate);
        }
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