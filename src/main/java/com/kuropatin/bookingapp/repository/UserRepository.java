package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}