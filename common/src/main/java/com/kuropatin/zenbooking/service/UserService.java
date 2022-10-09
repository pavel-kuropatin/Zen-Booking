package com.kuropatin.zenbooking.service;

import com.kuropatin.zenbooking.exception.EmailAlreadyInUseException;
import com.kuropatin.zenbooking.exception.InsufficientMoneyAmountException;
import com.kuropatin.zenbooking.exception.LoginAlreadyInUseException;
import com.kuropatin.zenbooking.exception.MoneyAmountExceededException;
import com.kuropatin.zenbooking.exception.UserNotFoundException;
import com.kuropatin.zenbooking.model.Gender;
import com.kuropatin.zenbooking.model.User;
import com.kuropatin.zenbooking.model.request.AmountRequest;
import com.kuropatin.zenbooking.model.request.UserCreateRequest;
import com.kuropatin.zenbooking.model.request.UserEditRequest;
import com.kuropatin.zenbooking.model.response.SuccessfulResponse;
import com.kuropatin.zenbooking.model.response.UserResponse;
import com.kuropatin.zenbooking.repository.UserRepository;
import com.kuropatin.zenbooking.util.ApplicationTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.MessageFormat;

@Repository
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean existsByLogin(final String login) {
        return repository.existsByLoginAndIsBannedFalse(login);
    }

    public User getUserById(final Long id) {
        if (repository.existsById(id)) {
            return repository.findUserByIdAndIsBannedFalse(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public User getUserByLogin(final String login) {
        if (existsByLogin(login)) {
            return repository.findUserByLoginAndIsBannedFalse(login);
        } else {
            throw new UserNotFoundException(login);
        }
    }

    public User createUser(final UserCreateRequest userCreateRequest) {
        if (repository.isLoginInUse(userCreateRequest.getLogin())) {
            throw new LoginAlreadyInUseException(userCreateRequest.getLogin());
        }
        if (repository.isEmailInUse(userCreateRequest.getEmail())) {
            throw new EmailAlreadyInUseException(userCreateRequest.getEmail());
        }
        final User user = transformToNewUser(userCreateRequest);
        user.setCreated(ApplicationTimeUtils.getTimestamp());
        user.setUpdated(user.getCreated());
        return repository.save(user);
    }

    public User updateUser(final Long id, final UserEditRequest userEditRequest) {
        final User userToUpdate = getUserById(id);
        if (!userToUpdate.getEmail().equals(userEditRequest.getEmail()) && repository.isEmailInUse(userEditRequest.getEmail())) {
            throw new EmailAlreadyInUseException(userEditRequest.getEmail());
        }
        transformToUser(userEditRequest, userToUpdate);
        userToUpdate.setUpdated(ApplicationTimeUtils.getTimestamp());
        return repository.save(userToUpdate);
    }

    public User deposit(final Long id, final AmountRequest amountRequest) {
        if (repository.isBanned(id)) {
            throw new UserNotFoundException(id);
        }
        final User user = getUserById(id);
        user.setBalance(user.getBalance() + amountRequest.getAmount());
        user.setUpdated(ApplicationTimeUtils.getTimestamp());
        return repository.save(user);
    }

    @Transactional(rollbackFor = {
            InsufficientMoneyAmountException.class,
            MoneyAmountExceededException.class
    })
    public void pay(final User user, final int amount, final Timestamp timestamp) {
        if (user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            user.setUpdated(timestamp);
            repository.save(user);
        } else {
            throw new InsufficientMoneyAmountException();
        }
    }

    @Transactional(rollbackFor = {
            InsufficientMoneyAmountException.class,
            MoneyAmountExceededException.class
    })
    public void transferMoney(final User user, final int amount, final Timestamp timestamp) {
        final long checkOverflow = (long) user.getBalance() + (long) amount;
        if (checkOverflow > Integer.MAX_VALUE) {
            throw new MoneyAmountExceededException();
        } else {
            user.setBalance(user.getBalance() + amount);
            user.setUpdated(timestamp);
            repository.save(user);
        }
    }

    public SuccessfulResponse banUser(final Long id) {
        if (repository.existsById(id)) {
            final Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
            repository.banUser(id, timestamp);
            return new SuccessfulResponse(timestamp, MessageFormat.format("User with id: {0} was banned", id));
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public SuccessfulResponse unbanUser(final Long id) {
        if (repository.existsById(id)) {
            final Timestamp timestamp = ApplicationTimeUtils.getTimestamp();
            repository.unbanUser(id, timestamp);
            return new SuccessfulResponse(timestamp, MessageFormat.format("User with id: {0} is no longer banned", id));
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public void transformToUser(final UserEditRequest userEditRequest, final User user) {
        user.setName(userEditRequest.getName());
        user.setSurname(userEditRequest.getSurname());
        user.setGender(Gender.valueOf(userEditRequest.getGender()));
        user.setBirthDate(userEditRequest.getBirthDate());
        user.setEmail(userEditRequest.getEmail());
        user.setPhone(userEditRequest.getPhone());
    }

    public User transformToNewUser(final UserCreateRequest userCreateRequest) {
        return transformToUser(userCreateRequest, new User());
    }

    private User transformToUser(final UserCreateRequest userCreateRequest, final User user) {
        user.setLogin(userCreateRequest.getLogin());
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setName(userCreateRequest.getName());
        user.setSurname(userCreateRequest.getSurname());
        user.setGender(Gender.valueOf(userCreateRequest.getGender()));
        user.setBirthDate(userCreateRequest.getBirthDate());
        user.setEmail(userCreateRequest.getEmail());
        user.setPhone(userCreateRequest.getPhone());
        return user;
    }

    public UserResponse transformToNewUserResponse(final User user) {
        return transformToUserResponse(user, new UserResponse());
    }

    private UserResponse transformToUserResponse(final User user, final UserResponse userResponse) {
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setGender(user.getGender());
        userResponse.setBirthDate(user.getBirthDate());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setBalance(user.getBalance());
        userResponse.setLastLogin(user.getLastLogin());
        return userResponse;
    }
}