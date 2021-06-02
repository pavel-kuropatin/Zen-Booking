package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(@PathVariable final Long userId) {
        return new ResponseEntity<>(service.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody final User user) {
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(@PathVariable final Long userId, @RequestBody final User user) {
        return new ResponseEntity<>(service.updateUser(userId, user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteById(@PathVariable final Long userId) {
        return new ResponseEntity<>(service.softDeleteUser(userId), HttpStatus.OK);
    }
}