package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.UserNotFoundException;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User update(Long id, User user) {
        User userToUpdate = getUserById(id);
        userToUpdate.setLogin(user.getLogin());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setBirthDate(user.getBirthDate());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhone(user.getPhone());
        return userToUpdate;
    }

    @Transactional
    public User deleteById(Long id) {
        User userToDelete = getUserById(id);
        repository.delete(userToDelete);
        return userToDelete;
    }
}