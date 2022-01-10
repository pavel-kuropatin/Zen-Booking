package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.util.ApplicationTimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {

    private String timestamp;
    private String status;
    private String exception;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(final Exception e, final HttpStatus status) {
        final StackTraceElement element = e.getStackTrace()[0];
        this.timestamp = ApplicationTimeUtils.getTimeString();
        this.status = status.value() + " " + status.getReasonPhrase();
        this.exception = e.getClass().getName() + " at " + element.getClassName() + ":Line " + element.getLineNumber();
        this.message = e.getMessage();
    }

    public ErrorResponse(final Exception e, final HttpStatus status, final Map<String, String> errors) {
        final StackTraceElement element = e.getStackTrace()[0];
        this.timestamp = ApplicationTimeUtils.getTimeString();
        this.status = status.value() + " " + status.getReasonPhrase();
        this.exception = e.getClass().getName() + " at " + element.getClassName() + ":Line " + element.getLineNumber();
        this.errors = new LinkedHashMap<>();
        this.errors.putAll(errors);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}