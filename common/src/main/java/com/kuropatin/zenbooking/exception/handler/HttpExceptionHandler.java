package com.kuropatin.zenbooking.exception.handler;

import com.kuropatin.zenbooking.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public final class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull final MethodArgumentNotValidException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleMethodArgumentNotValid() method called");
        final Map<String, String> validationErrors = new LinkedHashMap<>();
        e.getBindingResult().getAllErrors().forEach(
                error -> validationErrors.put(((FieldError) error).getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(new ErrorResponse(status, validationErrors), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NonNull final HttpRequestMethodNotSupportedException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleHttpRequestMethodNotSupported() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            @NonNull final HttpMediaTypeNotSupportedException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleHttpMediaTypeNotSupported() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            @NonNull final HttpMediaTypeNotAcceptableException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleHttpMediaTypeNotAcceptable() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMissingPathVariable(
            @NonNull final MissingPathVariableException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleMissingPathVariable() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            @NonNull final MissingServletRequestParameterException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleMissingServletRequestParameter() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleServletRequestBindingException(
            @NonNull final ServletRequestBindingException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleServletRequestBindingException() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleConversionNotSupported(
            @NonNull final ConversionNotSupportedException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleConversionNotSupported() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleTypeMismatch(
            @NonNull final TypeMismatchException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleTypeMismatch() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull final HttpMessageNotReadableException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleHttpMessageNotReadable() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            @NonNull final HttpMessageNotWritableException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleHttpMessageNotWritable() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            @NonNull final MissingServletRequestPartException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleMissingServletRequestPart() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleBindException(
            @NonNull final BindException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleBindException() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            @NonNull final NoHandlerFoundException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleNoHandlerFoundException() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
            @NonNull final AsyncRequestTimeoutException e,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleAsyncRequestTimeoutException() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull final Exception e,
            final Object body,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.info("HttpExceptionHandler.handleExceptionInternal() method called");
        return new ResponseEntity<>(new ErrorResponse(e, status), status);
    }
}