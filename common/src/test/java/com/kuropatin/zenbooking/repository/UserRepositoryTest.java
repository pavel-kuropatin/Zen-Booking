package com.kuropatin.zenbooking.repository;

import com.kuropatin.zenbooking.model.User;
import com.kuropatin.zenbooking.test.config.Beans;
import com.kuropatin.zenbooking.test.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ContextConfiguration(classes = {Beans.class})
@EntityScan("com.kuropatin.zenbooking")
@EnableAutoConfiguration
@EnableJpaRepositories("com.kuropatin.zenbooking")
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
        newUser.setIsBanned(isBanned);

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
        newUser.setIsBanned(isBanned);

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
        newUser.setIsBanned(isBanned);

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
        final User newUser = TestUtils.getUser();
        newUser.setIsBanned(isBanned);

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
}