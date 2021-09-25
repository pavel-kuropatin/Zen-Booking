package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.util.ApplicationTimestamp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
public abstract class AbstractErrorResponse {

    private Timestamp timestamp;
    private String status;
    private String exception;

    protected AbstractErrorResponse(Exception e, HttpStatus status) {
        this.timestamp = ApplicationTimestamp.getTimestampUTC();
        this.status = status.value() + " " + status.getReasonPhrase();
        this.exception = e.getClass().getSimpleName();
    }
}