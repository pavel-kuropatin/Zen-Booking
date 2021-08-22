package com.kuropatin.bookingapp.repository;

import com.kuropatin.bookingapp.model.Gender;
import com.kuropatin.bookingapp.model.Roles;
import com.kuropatin.bookingapp.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldBeTrueExistsByLoginAndIsBannedFalse() {
        //given
        final String login = "login";
        User newUser = new User(
                0L,
                Roles.ROLE_USER,
                login,
                "12345678",
                "Name",
                "Surname",
                Gender.MALE,
                LocalDate.parse("1990-01-01"),
                "email@gmail.com",
                "+375112223344",
                0,
                false,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                Collections.emptySet(),
                Collections.emptySet()
        );
        userRepository.save(newUser);

        //when
        boolean shouldBeTrue = userRepository.existsByLoginAndIsBannedFalse(login);

        //then
        assertTrue(shouldBeTrue);
    }

    @Test
    void testIsLoginInUse() {
        //given
        final String login = "login";
        final String username = "username";
        User newUser = new User(
                0L,
                Roles.ROLE_USER,
                login,
                "12345678",
                "Name",
                "Surname",
                Gender.MALE,
                LocalDate.parse("1990-01-01"),
                "email@gmail.com",
                "+375112223344",
                0,
                false,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                Collections.emptySet(),
                Collections.emptySet()
        );
        userRepository.save(newUser);

        //when
        boolean shouldBeTrue = userRepository.isLoginInUse(login);

        //then
        assertTrue(shouldBeTrue);

        //when
        boolean shouldBeFalse = userRepository.isLoginInUse(username);

        //then
        assertFalse(shouldBeFalse);
    }

    @Test
    void testIsEmailInUse() {
        //given
        final String email = "email@gmail.com";
        final String secondEmail = "not@email.com";
        User newUser = new User(
                0L,
                Roles.ROLE_USER,
                "login",
                "12345678",
                "Name",
                "Surname",
                Gender.MALE,
                LocalDate.parse("1990-01-01"),
                email,
                "+375112223344",
                0,
                false,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                Collections.emptySet(),
                Collections.emptySet()
        );
        userRepository.save(newUser);

        //when
        boolean shouldBeTrue = userRepository.isEmailInUse(email);

        //then
        assertTrue(shouldBeTrue);

        //when
        boolean shouldBeFalse = userRepository.isEmailInUse(secondEmail);

        //then
        assertFalse(shouldBeFalse);
    }

    @Test
    void shouldBeTrueIsBanned() {
        //given
        final boolean isBanned = true;
        User newUser = new User(
                0L,
                Roles.ROLE_USER,
                "login",
                "12345678",
                "Name",
                "Surname",
                Gender.MALE,
                LocalDate.parse("1990-01-01"),
                "email@gmail.com",
                "+375112223344",
                0,
                isBanned,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                Collections.emptySet(),
                Collections.emptySet()
        );
        User savedUser = userRepository.save(newUser);

        //when
        boolean shouldBeTrue = userRepository.isBanned(savedUser.getId());

        //then
        assertTrue(shouldBeTrue);
    }

    @Test
    void shouldBeFalseIsBanned() {
        //given
        final boolean isBanned = false;
        User newUser = new User(
                0L,
                Roles.ROLE_USER,
                "login",
                "12345678",
                "Name",
                "Surname",
                Gender.MALE,
                LocalDate.parse("1990-01-01"),
                "email@gmail.com",
                "+375112223344",
                0,
                isBanned,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                Collections.emptySet(),
                Collections.emptySet()
        );
        User savedUser = userRepository.save(newUser);

        //when
        boolean shouldBeFalse = userRepository.isBanned(savedUser.getId());

        //then
        assertFalse(shouldBeFalse);
    }

    @Test
    void testBanUser() {
        //given
        final boolean isBanned = false;
        User newUser = new User(
                0L,
                Roles.ROLE_USER,
                "login",
                "12345678",
                "Name",
                "Surname",
                Gender.MALE,
                LocalDate.parse("1990-01-01"),
                "email@gmail.com",
                "+375112223344",
                0,
                isBanned,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                Collections.emptySet(),
                Collections.emptySet()
        );
        User savedUser = userRepository.save(newUser);

        //when
        boolean shouldBeFalse = userRepository.isBanned(savedUser.getId());

        //then
        assertFalse(shouldBeFalse);

        //when
        userRepository.banUser(savedUser.getId(), Timestamp.valueOf(LocalDateTime.now()));
        boolean shouldBeTrue = userRepository.isBanned(savedUser.getId());

        //then
        assertTrue(shouldBeTrue);
    }

    @Test
    void testUnbanUser() {
        //given
        final boolean isBanned = true;
        User newUser = new User(
                0L,
                Roles.ROLE_USER,
                "login",
                "12345678",
                "Name",
                "Surname",
                Gender.MALE,
                LocalDate.parse("1990-01-01"),
                "email@gmail.com",
                "+375112223344",
                0,
                isBanned,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                Collections.emptySet(),
                Collections.emptySet()
        );
        User savedUser = userRepository.save(newUser);

        //when
        boolean shouldBeTrue = userRepository.isBanned(savedUser.getId());

        //then
        assertTrue(shouldBeTrue);

        //when
        userRepository.unbanUser(savedUser.getId(), Timestamp.valueOf(LocalDateTime.now()));
        boolean shouldBeFalse = userRepository.isBanned(savedUser.getId());

        //then
        assertFalse(shouldBeFalse);
    }
}