package com.kuropatin.bookingapp.model.response;

import com.kuropatin.bookingapp.util.ApplicationTimestamp;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
public class ErrorResponse {

    private Timestamp timestamp;
    private String status;
    private String exception;
    private String message;

    public ErrorResponse(Exception e, HttpStatus status) {
        this.timestamp = ApplicationTimestamp.getTimestampUTC();
        this.status = status.value() + " " + status.getReasonPhrase();
        this.exception = e.getClass().getSimpleName();
        this.message = e.getMessage();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}