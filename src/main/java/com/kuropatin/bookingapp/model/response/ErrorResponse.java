package com.kuropatin.bookingapp.model.response;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
public class ErrorResponse {

    private Timestamp timestamp;
    private String status;
    private String exception;
    private String message;

    public ErrorResponse(Exception e, HttpStatus status) {
        this.timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));
        this.status = status.value() + " " + status.getReasonPhrase();
        this.exception = e.getClass().getSimpleName();
        this.message = e.getMessage();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}