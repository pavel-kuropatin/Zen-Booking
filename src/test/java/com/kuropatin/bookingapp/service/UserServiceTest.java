package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.EmailAlreadyInUseException;
import com.kuropatin.bookingapp.exception.InsufficientMoneyAmountException;
import com.kuropatin.bookingapp.exception.LoginAlreadyInUseException;
import com.kuropatin.bookingapp.exception.MoneyAmountExceededException;
import com.kuropatin.bookingapp.exception.UserNotFoundException;
import com.kuropatin.bookingapp.model.Gender;
import com.kuropatin.bookingapp.model.Roles;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.request.AmountRequest;
import com.kuropatin.bookingapp.model.request.UserCreateRequest;
import com.kuropatin.bookingapp.model.request.UserEditRequest;
import com.kuropatin.bookingapp.model.response.UserResponse;
import com.kuropatin.bookingapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void existsByLogin() {
        //given
        String login = "login";

        //when
        userService.existsByLogin(login);

        //then
        verify(userRepository).existsByLoginAndIsBannedFalse(login);
    }

    @Test
    void returnsUserById() {
        //given
        final Long id = 1L;
        given(userRepository.existsById(id)).willReturn(true);

        //when
        userService.getUserById(id);

        //then
        verify(userRepository).findUserByIdAndIsBannedFalse(id);
    }

    @Test
    void throwsExceptionIfUserNotExistsById() {
        //given
        final Long id = 1L;
        given(userRepository.existsById(id)).willReturn(false);

        //when, then
        assertThatThrownBy(() -> userService.getUserById(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(MessageFormat.format("Could not find user with id: {0}", id));
    }

    @Test
    void returnsUserByLogin() {
        //given
        final String login = "login";
        given(userRepository.existsByLoginAndIsBannedFalse(login)).willReturn(true);

        //when
        userService.getUserByLogin(login);

        //then
        verify(userRepository).findUserByLoginAndIsBannedFalse(login);
    }

    @Test
    void throwsExceptionIfUserNotExistsByLogin() {
        //given
        final String login = "login";
        given(userRepository.existsByLoginAndIsBannedFalse(login)).willReturn(false);

        //when, then
        assertThatThrownBy(() -> userService.getUserByLogin(login))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(MessageFormat.format("Could not find user with login: {0}", login));
    }

    @Test
    void canCreateUser() {
        //given
        final String login = "login";
        final String email = "email@gmail.com";
        UserCreateRequest userCreateRequest = new UserCreateRequest(
                login,
                "12345678",
                "Name",
                "Surname",
                "MALE",
                "1990-01-01",
                email,
                "+375112223344"
        );
        given(userRepository.isLoginInUse(login)).willReturn(false);
        given(userRepository.isEmailInUse(email)).willReturn(false);

        //when
        userService.createUser(userCreateRequest);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getLogin(), userCreateRequest.getLogin());
        assertTrue(passwordEncoder.matches(userCreateRequest.getPassword(), capturedUser.getPassword()));
        assertEquals(capturedUser.getName(), userCreateRequest.getName());
        assertEquals(capturedUser.getSurname(), userCreateRequest.getSurname());
        assertEquals(capturedUser.getGender(), Gender.valueOf(userCreateRequest.getGender()));
        assertEquals(capturedUser.getBirthDate(), LocalDate.parse(userCreateRequest.getBirthDate()));
        assertEquals(capturedUser.getEmail(), userCreateRequest.getEmail());
        assertEquals(capturedUser.getPhone(), userCreateRequest.getPhone());
        assertNotNull(capturedUser.getCreated());
        assertNotNull(capturedUser.getUpdated());
    }

    @Test
    void throwsExceptionIfLoginInUseOnCreate() {
        //given
        final String login = "login";
        final String email = "email@gmail.com";
        UserCreateRequest userCreateRequest = new UserCreateRequest(
                login,
                "12345678",
                "Name",
                "Surname",
                "MALE",
                "1990-01-01",
                email,
                "+375112223344"
        );
        given(userRepository.isLoginInUse(login)).willReturn(true);

        //when, then
        assertThatThrownBy(() -> userService.createUser(userCreateRequest))
                .isInstanceOf(LoginAlreadyInUseException.class)
                .hasMessageContaining(MessageFormat.format("Login {0} already in use", login));
    }

    @Test
    void throwsExceptionIfEmailInUseOnCreate() {
        //given
        final String login = "login";
        final String email = "email@gmail.com";
        UserCreateRequest userCreateRequest = new UserCreateRequest(
                login,
                "12345678",
                "Name",
                "Surname",
                "MALE",
                "1990-01-01",
                email,
                "+375112223344"
        );
        given(userRepository.isEmailInUse(email)).willReturn(true);

        //when, then
        assertThatThrownBy(() -> userService.createUser(userCreateRequest))
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessageContaining(MessageFormat.format("Email {0} already in use", email));
    }

    @Test
    void canUpdateUser() {
        //given
        final Long id = 1L;

        UserEditRequest userEditRequest = new UserEditRequest(
                "New Name",
                "New Surname",
                "MALE",
                "1991-12-12",
                "new-email@gmail.com",
                "+375998887766"
        );

        given(userRepository.existsById(id)).willReturn(true);
        given(userRepository.findUserByIdAndIsBannedFalse(id)).willReturn(getUserForTest());

        //when
        userService.updateUser(id, userEditRequest);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(userEditRequest.getName(), capturedUser.getName());
        assertEquals(userEditRequest.getSurname(), capturedUser.getSurname());
        assertEquals(Gender.valueOf(userEditRequest.getGender()), capturedUser.getGender());
        assertEquals(LocalDate.parse(userEditRequest.getBirthDate()), capturedUser.getBirthDate());
        assertEquals(userEditRequest.getEmail(), capturedUser.getEmail());
        assertEquals(userEditRequest.getPhone(), capturedUser.getPhone());
    }

    @Test
    void throwsExceptionIfEmailInUseOnUpdate() {
        //given
        final Long id = 1L;
        final String email = "new-email@gmail.com";

        UserEditRequest userEditRequest = new UserEditRequest(
                "New Name",
                "New Surname",
                "MALE",
                "1991-12-12",
                email,
                "+375998887766"
        );

        given(userRepository.existsById(id)).willReturn(true);
        given(userRepository.findUserByIdAndIsBannedFalse(id)).willReturn(getUserForTest());
        given(userRepository.isEmailInUse(email)).willReturn(true);

        //when, then
        assertThatThrownBy(() -> userService.updateUser(id, userEditRequest))
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessageContaining(MessageFormat.format("Email {0} already in use", email));
    }

    @Test
    void canDeposit() {
        //given
        final Long id = 1L;
        AmountRequest amountRequest = new AmountRequest("123");
        given(userRepository.isBanned(id)).willReturn(false);
        given(userRepository.existsById(id)).willReturn(true);
        given(userRepository.findUserByIdAndIsBannedFalse(id)).willReturn(getUserForTest());

        //when
        userService.deposit(id, amountRequest);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(323, capturedUser.getBalance());
    }

    @Test
    void throwsExceptionIfBannedOnDeposit() {
        //given
        final Long id = 1L;
        AmountRequest amountRequest = new AmountRequest("123");
        given(userRepository.isBanned(id)).willReturn(true);

        //when, then
        assertThatThrownBy(() -> userService.deposit(id, amountRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(MessageFormat.format("Could not find user with id: {0}", id));
    }

    @Test
    void canPay() {
        //given
        final int amount = 100;
        User user = getUserForTest();
        final int expected = user.getBalance() - amount;
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));

        //when
        userService.pay(user, amount, timestamp);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(expected, capturedUser.getBalance());
        assertEquals(timestamp, capturedUser.getUpdated());
    }

    @Test
    void throwsExceptionIfNotEnoughMoney() {
        //given
        final int amount = 300;
        User user = getUserForTest();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));

        //when, then
        assertThatThrownBy(() -> userService.pay(user, amount, timestamp))
                .isInstanceOf(InsufficientMoneyAmountException.class)
                .hasMessageContaining("Insufficient money amount");
    }

    @Test
    void canTransferMoney() {
        //given
        final int amount = 100;
        User user = getUserForTest();
        final int expected = user.getBalance() + amount;
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));

        //when
        userService.transferMoney(user, amount, timestamp);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(expected, capturedUser.getBalance());
        assertEquals(timestamp, capturedUser.getUpdated());
    }

    @Test
    void throwsExceptionOnMoneyOverflow() {
        //given
        final int amount = Integer.MAX_VALUE;
        User user = getUserForTest();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));

        //when, then
        assertThatThrownBy(() -> userService.transferMoney(user, amount, timestamp))
                .isInstanceOf(MoneyAmountExceededException.class)
                .hasMessageContaining("The maximum amount of money has been exceeded");
    }

    @Test
    void canBanUser() {
        //given
        final Long id = 1L;
        given(userRepository.existsById(id)).willReturn(true);

        //when
        userService.banUser(id);

        //then
        ArgumentCaptor<Timestamp> timestampArgumentCaptor = ArgumentCaptor.forClass(Timestamp.class);
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).banUser(idArgumentCaptor.capture(), timestampArgumentCaptor.capture());
        Timestamp capturedTimestamp = timestampArgumentCaptor.getValue();
        verify(userRepository).banUser(id, capturedTimestamp);
    }

    @Test
    void throwsExceptionOnBan() {
        //given
        final Long id = 1L;

        //when, then
        assertThatThrownBy(() -> userService.banUser(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(MessageFormat.format("Could not find user with id: {0}", id));
    }

    @Test
    void canUnbanUser() {
        //given
        final Long id = 1L;
        given(userRepository.existsById(id)).willReturn(true);

        //when
        userService.unbanUser(id);

        //then
        ArgumentCaptor<Timestamp> timestampArgumentCaptor = ArgumentCaptor.forClass(Timestamp.class);
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).unbanUser(idArgumentCaptor.capture(), timestampArgumentCaptor.capture());
        Timestamp capturedTimestamp = timestampArgumentCaptor.getValue();
        verify(userRepository).unbanUser(id, capturedTimestamp);
    }

    @Test
    void throwsExceptionOnUnban() {
        //given
        final Long id = 1L;

        //when, then
        assertThatThrownBy(() -> userService.unbanUser(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(MessageFormat.format("Could not find user with id: {0}", id));
    }

    @Test
    void canTransformToUserFromUserCreateRequest() {
        //given
        User user = getUserForTest();
        UserEditRequest userEditRequest = new UserEditRequest(
                "John",
                "Doe",
                "MALE",
                "1991-12-12",
                "new-email@gmail.com",
                "+375998887766"
        );

        //when
        userService.transformToUser(userEditRequest, user);

        //then
        assertEquals(user.getName(), userEditRequest.getName());
        assertEquals(user.getSurname(), userEditRequest.getSurname());
        assertEquals(user.getGender(), Gender.valueOf(userEditRequest.getGender()));
        assertEquals(user.getBirthDate(), LocalDate.parse(userEditRequest.getBirthDate()));
        assertEquals(user.getEmail(), userEditRequest.getEmail());
        assertEquals(user.getPhone(), userEditRequest.getPhone());
    }

    @Test
    void canTransformToNewUserFromUserCreateRequest() {
        //given
        UserCreateRequest userCreateRequest = new UserCreateRequest(
                "login",
                "12345678",
                "Name",
                "Surname",
                "MALE",
                "1990-01-01",
                "email@gmail.com",
                "+375112223344"
        );

        //when
        User transformedUser = userService.transformToNewUser(userCreateRequest);

        //then
        assertEquals(transformedUser.getLogin(), userCreateRequest.getLogin());
        assertTrue(passwordEncoder.matches(userCreateRequest.getPassword(), transformedUser.getPassword()));
        assertEquals(transformedUser.getName(), userCreateRequest.getName());
        assertEquals(transformedUser.getSurname(), userCreateRequest.getSurname());
        assertEquals(transformedUser.getGender(), Gender.valueOf(userCreateRequest.getGender()));
        assertEquals(transformedUser.getBirthDate(), LocalDate.parse(userCreateRequest.getBirthDate()));
        assertEquals(transformedUser.getEmail(), userCreateRequest.getEmail());
        assertEquals(transformedUser.getPhone(), userCreateRequest.getPhone());
    }

    @Test
    void transformToNewUserResponse() {
        //given
        User user = getUserForTest();

        //when
        UserResponse userResponse = userService.transformToNewUserResponse(user);

        //then
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(user.getName(), userResponse.getName());
        assertEquals(user.getSurname(), userResponse.getSurname());
        assertEquals(user.getGender(), userResponse.getGender());
        assertEquals(user.getBirthDate(), userResponse.getBirthDate());
        assertEquals(user.getEmail(), userResponse.getEmail());
        assertEquals(user.getPhone(), userResponse.getPhone());
        assertEquals(user.getBalance(), userResponse.getBalance());
    }

    private User getUserForTest() {
        return new User(
                100L,
                Roles.ROLE_USER,
                "login",
                "12345678",
                "Name",
                "Surname",
                Gender.UNDEFINED,
                LocalDate.parse("1990-01-01"),
                "email@gmail.com",
                "+375112223344",
                200,
                false,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                Collections.emptySet(),
                Collections.emptySet()
        );
    }
}