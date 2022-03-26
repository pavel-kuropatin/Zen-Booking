package com.kuropatin.zenbooking.test.utils;

import com.kuropatin.zenbooking.model.Gender;
import com.kuropatin.zenbooking.model.Roles;
import com.kuropatin.zenbooking.model.User;
import com.kuropatin.zenbooking.model.request.AmountRequest;
import com.kuropatin.zenbooking.model.request.UserCreateRequest;
import com.kuropatin.zenbooking.model.request.UserEditRequest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class TestUtils {

    private TestUtils() {
    }

    public static User getUser() {
        final User user = new User();
        user.setRole(Roles.ROLE_USER);
        user.setLogin("login");
        user.setPassword("12345678");
        user.setName("Name");
        user.setSurname("Surname");
        user.setGender(Gender.UNDEFINED);
        user.setBirthDate(LocalDate.parse("1990-01-01"));
        user.setEmail("email@gmail.com");
        user.setPhone("+375112223344");
        user.setBalance(200);
        user.setIsBanned(false);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }

    public static UserCreateRequest getUserCreateRequest() {
        final UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setLogin("login");
        userCreateRequest.setPassword("12345678");
        userCreateRequest.setName("Name");
        userCreateRequest.setSurname("Surname");
        userCreateRequest.setGender("UNDEFINED");
        userCreateRequest.setBirthDate("1990-01-01");
        userCreateRequest.setEmail("email@gmail.com");
        userCreateRequest.setPhone("+375999999999");
        return userCreateRequest;
    }

    public static UserEditRequest getUserEditRequest() {
        final UserEditRequest userEditRequest = new UserCreateRequest();
        userEditRequest.setName("New Name");
        userEditRequest.setSurname("NewSurname");
        userEditRequest.setGender("MALE");
        userEditRequest.setBirthDate("1991-12-12");
        userEditRequest.setEmail("new-email@gmail.com");
        userEditRequest.setPhone("+375111111111");
        return userEditRequest;
    }

    public static AmountRequest getAmountRequest() {
        final AmountRequest amountRequest = new AmountRequest();
        amountRequest.setAmount(123);
        return amountRequest;
    }
}