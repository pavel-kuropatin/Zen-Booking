package com.kuropatin.zenbooking.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kuropatin.zenbooking.util.ApplicationTimeUtils;
import com.kuropatin.zenbooking.util.ToStringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String timestamp;
    private Integer status;
    private String exception;
    private String message;
    private Map<String, String> validationErrors;

    public ErrorResponse(final Exception e, final HttpStatus status) {
        final StackTraceElement element = e.getStackTrace()[0];
        this.timestamp = ApplicationTimeUtils.getTimeString();
        this.status = status.value();
        this.exception = e.getClass().getName() + " at " + element.toString();
        this.message = e.getMessage();
    }

    public ErrorResponse(final HttpStatus status, final Map<String, String> validationErrors) {
        this.timestamp = ApplicationTimeUtils.getTimeString();
        this.status = status.value();
        this.validationErrors = new LinkedHashMap<>();
        this.validationErrors.putAll(validationErrors);
    }

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}