package com.kuropatin.zenbooking.exception;

import com.kuropatin.zenbooking.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> badCredentialsHandler(BadCredentialsException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> userNotFoundHandler(UserNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    protected ResponseEntity<Object> propertyNotFoundHandler(PropertyNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyImageNotFoundException.class)
    protected ResponseEntity<Object> propertyImageNotFoundHandler(PropertyImageNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<Object> orderNotFoundHandler(OrderNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    protected ResponseEntity<Object> reviewNotFoundHandler(ReviewNotFoundException e) {
        return throwCustomException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewCannotBeAddedException.class)
    protected ResponseEntity<Object> reviewCannotBeAddedHandler(ReviewCannotBeAddedException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientMoneyAmountException.class)
    protected ResponseEntity<Object> insufficientMoneyAmountHandler(InsufficientMoneyAmountException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MoneyAmountExceededException.class)
    protected ResponseEntity<Object> moneyAmountExceededHandler(MoneyAmountExceededException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginAlreadyInUseException.class)
    protected ResponseEntity<Object> loginAlreadyInUseHandler(LoginAlreadyInUseException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    protected ResponseEntity<Object> emailAlreadyInUseHandler(EmailAlreadyInUseException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCannotBeAcceptedException.class)
    protected ResponseEntity<Object> orderCannotBeAcceptedHandler(OrderCannotBeAcceptedException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCannotBeCancelledException.class)
    protected ResponseEntity<Object> orderCannotBeCancelledHandler(OrderCannotBeCancelledException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCannotBeDeclinedException.class)
    protected ResponseEntity<Object> orderCannotBeDeclinedHandler(OrderCannotBeDeclinedException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyCannotBeOrderedException.class)
    protected ResponseEntity<Object> orderCannotBeProcessedHandler(PropertyCannotBeOrderedException e) {
        return throwCustomException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QueryBuilderException.class)
    protected ResponseEntity<Object> queryBuilderHandler(QueryBuilderException e) {
        return throwCustomException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> throwCustomException(RuntimeException e, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }
}