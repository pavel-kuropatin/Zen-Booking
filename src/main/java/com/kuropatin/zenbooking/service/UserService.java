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
import com.kuropatin.zenbooking.util.ApplicationTimestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDate;

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
        user.setCreated(ApplicationTimestamp.getTimestampUTC());
        user.setUpdated(user.getCreated());
        return repository.save(user);
    }

    public User updateUser(Long id, UserEditRequest userEditRequest) {
        User userToUpdate = getUserById(id);
        if(!userToUpdate.getEmail().equals(userEditRequest.getEmail()) && repository.isEmailInUse(userEditRequest.getEmail())) {
            throw new EmailAlreadyInUseException(userEditRequest.getEmail());
        }
        transformToUser(userEditRequest, userToUpdate);
        userToUpdate.setUpdated(ApplicationTimestamp.getTimestampUTC());
        return repository.save(userToUpdate);
    }

    public User deposit(Long id, AmountRequest amountRequest) {
        if(repository.isBanned(id)) {
            throw new UserNotFoundException(id);
        }
        User user = getUserById(id);
        user.setBalance(user.getBalance() + Integer.parseInt(amountRequest.getAmount()));
        user.setUpdated(ApplicationTimestamp.getTimestampUTC());
        return repository.save(user);
    }

    @Transactional(rollbackFor = {
            InsufficientMoneyAmountException.class,
            MoneyAmountExceededException.class
    })
    public void pay(User user, int amount, Timestamp timestamp) {
        if(user.getBalance() >= amount) {
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
    public void transferMoney(User user, int amount, Timestamp timestamp) {
        long checkOverflow = (long) user.getBalance() + (long) amount;
        if (checkOverflow > Integer.MAX_VALUE) {
            throw new MoneyAmountExceededException();
        } else {
            user.setBalance(user.getBalance() + amount);
            user.setUpdated(timestamp);
            repository.save(user);
        }
    }

    public SuccessfulResponse banUser(Long id) {
        if(repository.existsById(id)) {
            Timestamp timestamp = ApplicationTimestamp.getTimestampUTC();
            repository.banUser(id, timestamp);
            return new SuccessfulResponse(timestamp, MessageFormat.format("User with id: {0} was banned", id));
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public SuccessfulResponse unbanUser(Long id) {
        if(repository.existsById(id)) {
            Timestamp timestamp = ApplicationTimestamp.getTimestampUTC();
            repository.unbanUser(id, timestamp);
            return new SuccessfulResponse(timestamp, MessageFormat.format("User with id: {0} is no longer banned", id));
        } else {
            throw new UserNotFoundException(id);
        }
    }

    public User transformToUser(UserEditRequest userEditRequest, User user) {
        user.setName(userEditRequest.getName());
        user.setSurname(userEditRequest.getSurname());
        user.setGender(Gender.valueOf(userEditRequest.getGender()));
        user.setBirthDate(LocalDate.parse(userEditRequest.getBirthDate()));
        user.setEmail(userEditRequest.getEmail());
        user.setPhone(userEditRequest.getPhone());
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