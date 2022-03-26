package com.kuropatin.zenbooking.validation.handler;

import com.kuropatin.zenbooking.model.response.ErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException e, @NonNull final HttpHeaders headers, @NonNull final HttpStatus status, @NonNull final WebRequest request) {
        final Map<String, String> validationErrors = new LinkedHashMap<>();
        e.getBindingResult().getAllErrors().forEach(
                error -> validationErrors.put(((FieldError) error).getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(new ErrorResponse(status, validationErrors), status);
    }
}
