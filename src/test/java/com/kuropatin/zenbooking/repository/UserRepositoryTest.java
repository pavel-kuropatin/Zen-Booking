package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.model.Gender;
import com.kuropatin.zenbooking.model.Roles;
import com.kuropatin.zenbooking.model.User;
import com.kuropatin.zenbooking.test.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        final User newUser = TestUtils.getUser();
        newUser.setLogin(login);
        userRepository.save(newUser);

        //when
        final boolean shouldBeTrue = userRepository.existsByLoginAndIsBannedFalse(login);

        //then
        assertTrue(shouldBeTrue);
    }

    @Test
    void testIsLoginInUse() {
        //given
        final String login = "login";
        final String username = "username";
        final User newUser = TestUtils.getUser();
        newUser.setLogin(login);
        userRepository.save(newUser);

        //when
        final boolean shouldBeTrue = userRepository.isLoginInUse(login);

        //then
        assertTrue(shouldBeTrue);

        //when
        final boolean shouldBeFalse = userRepository.isLoginInUse(username);

        //then
        assertFalse(shouldBeFalse);
    }

    @Test
    void testIsEmailInUse() {
        //given
        final String email = "email@gmail.com";
        final String secondEmail = "not@email.com";
        final User newUser = TestUtils.getUser();
        newUser.setEmail(email);
        userRepository.save(newUser);

        //when
        final boolean shouldBeTrue = userRepository.isEmailInUse(email);

        //then
        assertTrue(shouldBeTrue);

        //when
        final boolean shouldBeFalse = userRepository.isEmailInUse(secondEmail);

        //then
        assertFalse(shouldBeFalse);
    }

    @Test
    void shouldBeTrueIsBanned() {
        //given
        final boolean isBanned = true;
        final User newUser = TestUtils.getUser();
        newUser.setBanned(isBanned);

        final User savedUser = userRepository.save(newUser);

        //when
        final boolean shouldBeTrue = userRepository.isBanned(savedUser.getId());

        //then
        assertTrue(shouldBeTrue);
    }

    @Test
    void shouldBeFalseIsBanned() {
        //given
        final boolean isBanned = false;
        final User newUser = TestUtils.getUser();
        newUser.setBanned(isBanned);

        final User savedUser = userRepository.save(newUser);

        //when
        final boolean shouldBeFalse = userRepository.isBanned(savedUser.getId());

        //then
        assertFalse(shouldBeFalse);
    }

    @Test
    void testBanUser() {
        //given
        final boolean isBanned = false;
        final User newUser = TestUtils.getUser();
        newUser.setBanned(isBanned);

        final User savedUser = userRepository.save(newUser);

        //when
        final boolean shouldBeFalse = userRepository.isBanned(savedUser.getId());

        //then
        assertFalse(shouldBeFalse);

        //when
        userRepository.banUser(savedUser.getId(), Timestamp.valueOf(LocalDateTime.now()));
        final boolean shouldBeTrue = userRepository.isBanned(savedUser.getId());

        //then
        assertTrue(shouldBeTrue);
    }

    @Test
    void testUnbanUser() {
        //given
        final boolean isBanned = true;
        final User newUser = getUserForTest();
        newUser.setBanned(isBanned);

        final User savedUser = userRepository.save(newUser);

        //when
        final boolean shouldBeTrue = userRepository.isBanned(savedUser.getId());

        //then
        assertTrue(shouldBeTrue);

        //when
        userRepository.unbanUser(savedUser.getId(), Timestamp.valueOf(LocalDateTime.now()));
        final boolean shouldBeFalse = userRepository.isBanned(savedUser.getId());

        //then
        assertFalse(shouldBeFalse);
    }

    private User getUserForTest() {
        final User user = new User();
        user.setId(0L);
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
        user.setBanned(false);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }
}