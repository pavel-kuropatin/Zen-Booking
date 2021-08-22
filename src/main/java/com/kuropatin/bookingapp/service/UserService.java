package com.kuropatin.bookingapp.service;

import com.kuropatin.bookingapp.exception.EmailAlreadyInUseException;
import com.kuropatin.bookingapp.exception.InsufficientMoneyAmountException;
import com.kuropatin.bookingapp.exception.LoginAlreadyInUseException;
import com.kuropatin.bookingapp.exception.MoneyAmountExceededException;
import com.kuropatin.bookingapp.exception.UserNotFoundException;
import com.kuropatin.bookingapp.model.Gender;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.model.request.AmountRequest;
import com.kuropatin.bookingapp.model.request.UserCreateRequest;
import com.kuropatin.bookingapp.model.request.UserEditRequest;
import com.kuropatin.bookingapp.model.response.UserResponse;
import com.kuropatin.bookingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Repository
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean existsByLogin(String login) {
        return repository.existsByLoginAndIsBannedFalse(login);
    }

    public User getUserById(Long id) {
        if(repository.existsById(id)) {
            return repository.findUserByIdAndIsBannedFalse(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public User getUserByLogin(String login) {
        if(existsByLogin(login)) {
            return repository.findUserByLoginAndIsBannedFalse(login);
        } else {
            throw new UserNotFoundException(login);
        }
    }

    public User createUser(UserCreateRequest userCreateRequest) {
        if(repository.isLoginInUse(userCreateRequest.getLogin())) {
            throw new LoginAlreadyInUseException(userCreateRequest.getLogin());
        }
        if(repository.isEmailInUse(userCreateRequest.getEmail())) {
            throw new EmailAlreadyInUseException(userCreateRequest.getEmail());
        }
        User user = transformToNewUser(userCreateRequest);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        user.setUpdated(user.getCreated());
        return repository.save(user);
    }

    public User updateUser(Long id, UserEditRequest userEditRequest) {
        User userToUpdate = getUserById(id);
        if(!userToUpdate.getEmail().equals(userEditRequest.getEmail()) && repository.isEmailInUse(userEditRequest.getEmail())) {
            throw new EmailAlreadyInUseException(userEditRequest.getEmail());
        }
        transformToUser(userEditRequest, userToUpdate);
        userToUpdate.setUpdated(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        return repository.save(userToUpdate);
    }

    public User deposit(Long id, AmountRequest amountRequest) {
        if(repository.isBanned(id)) {
            throw new UserNotFoundException(id);
        }
        User user = getUserById(id);
        user.setBalance(user.getBalance() + Integer.parseInt(amountRequest.getAmount()));
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        return repository.save(user);
    }

    @Transactional(rollbackFor = {
            InsufficientMoneyAmountException.class,
            MoneyAmountExceededException.class
    })
    public void pay(User user, int amount) {
        if(user.getBalance() >= amount) {
            long checkOverflow = (long) user.getBalance() + (long) amount;
            if (checkOverflow > Integer.MAX_VALUE) {
                throw new MoneyAmountExceededException();
            } else {
                user.setBalance(user.getBalance() - amount);
                user.setUpdated(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
                repository.save(user);
            }
        } else {
            throw new InsufficientMoneyAmountException();
        }
    }

    @Transactional(rollbackFor = {
            InsufficientMoneyAmountException.class,
            MoneyAmountExceededException.class
    })
    public void transferMoney(User user, int amount) {
        long checkOverflow = (long) user.getBalance() + (long) amount;
        if (checkOverflow > Integer.MAX_VALUE) {
            throw new MoneyAmountExceededException();
        } else {
            user.setBalance(user.getBalance() + amount);
            user.setUpdated(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
            repository.save(user);
        }
    }

    public String banUser(Long id) {
        if(repository.existsById(id)) {
            repository.banUser(id, Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
            return MessageFormat.format("User with id: {0} was banned", id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public String unbanUser(Long id) {
        if(repository.existsById(id)) {
            repository.unbanUser(id, Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
            return MessageFormat.format("User with id: {0} is no longer banned", id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public User transformToUser(UserEditRequest userCreateRequest, User user) {
        user.setName(userCreateRequest.getName());
        user.setSurname(userCreateRequest.getSurname());
        user.setGender(Gender.valueOf(userCreateRequest.getGender()));
        user.setBirthDate(LocalDate.parse(userCreateRequest.getBirthDate()));
        user.setEmail(userCreateRequest.getEmail());
        user.setPhone(userCreateRequest.getPhone());
        return user;
    }

    public User transformToNewUser(UserCreateRequest userCreateRequest) {
        return transformToUser(userCreateRequest, new User());
    }

    private User transformToUser(UserCreateRequest userCreateRequest, User user) {
        user.setLogin(userCreateRequest.getLogin());
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setName(userCreateRequest.getName());
        user.setSurname(userCreateRequest.getSurname());
        user.setGender(Gender.valueOf(userCreateRequest.getGender()));
        user.setBirthDate(LocalDate.parse(userCreateRequest.getBirthDate()));
        user.setEmail(userCreateRequest.getEmail());
        user.setPhone(userCreateRequest.getPhone());
        return user;
    }

    public UserResponse transformToNewUserResponse(User user) {
        return transformToUserResponse(user, new UserResponse());
    }

    private UserResponse transformToUserResponse(User user, UserResponse userResponse) {
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setGender(user.getGender());
        userResponse.setBirthDate(user.getBirthDate());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setBalance(user.getBalance());
        return userResponse;
    }
}