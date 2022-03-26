package com.kuropatin.zenbooking.exception.handler;

import com.kuropatin.zenbooking.exception.ApplicationException;
import com.kuropatin.zenbooking.exception.BadCredentialsException;
import com.kuropatin.zenbooking.exception.EmailAlreadyInUseException;
import com.kuropatin.zenbooking.exception.InsufficientMoneyAmountException;
import com.kuropatin.zenbooking.exception.LoginAlreadyInUseException;
import com.kuropatin.zenbooking.exception.MoneyAmountExceededException;
import com.kuropatin.zenbooking.exception.OrderCannotBeAcceptedException;
import com.kuropatin.zenbooking.exception.OrderCannotBeCancelledException;
import com.kuropatin.zenbooking.exception.OrderCannotBeDeclinedException;
import com.kuropatin.zenbooking.exception.OrderNotFoundException;
import com.kuropatin.zenbooking.exception.PropertyCannotBeOrderedException;
import com.kuropatin.zenbooking.exception.PropertyImageNotFoundException;
import com.kuropatin.zenbooking.exception.PropertyNotFoundException;
import com.kuropatin.zenbooking.exception.ReviewCannotBeAddedException;
import com.kuropatin.zenbooking.exception.ReviewNotFoundException;
import com.kuropatin.zenbooking.exception.UserNotFoundException;
import com.kuropatin.zenbooking.model.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public final class ApplicationExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialsHandler(final BadCredentialsException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundHandler(final UserNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<Object> propertyNotFoundHandler(final PropertyNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyImageNotFoundException.class)
    public ResponseEntity<Object> propertyImageNotFoundHandler(final PropertyImageNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> orderNotFoundHandler(final OrderNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<Object> reviewNotFoundHandler(final ReviewNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewCannotBeAddedException.class)
    public ResponseEntity<Object> reviewCannotBeAddedHandler(final ReviewCannotBeAddedException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientMoneyAmountException.class)
    public ResponseEntity<Object> insufficientMoneyAmountHandler(final InsufficientMoneyAmountException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MoneyAmountExceededException.class)
    public ResponseEntity<Object> moneyAmountExceededHandler(final MoneyAmountExceededException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginAlreadyInUseException.class)
    public ResponseEntity<Object> loginAlreadyInUseHandler(final LoginAlreadyInUseException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<Object> emailAlreadyInUseHandler(final EmailAlreadyInUseException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCannotBeAcceptedException.class)
    public ResponseEntity<Object> orderCannotBeAcceptedHandler(final OrderCannotBeAcceptedException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCannotBeCancelledException.class)
    public ResponseEntity<Object> orderCannotBeCancelledHandler(final OrderCannotBeCancelledException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCannotBeDeclinedException.class)
    public ResponseEntity<Object> orderCannotBeDeclinedHandler(final OrderCannotBeDeclinedException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyCannotBeOrderedException.class)
    public ResponseEntity<Object> orderCannotBeProcessedHandler(final PropertyCannotBeOrderedException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> appExceptionHandler(final ApplicationException e) {
        return throwCustomException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> throwCustomException(final RuntimeException e, final HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }
}