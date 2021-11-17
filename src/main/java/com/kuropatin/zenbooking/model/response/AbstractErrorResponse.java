package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.util.ApplicationTimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AbstractErrorResponse {

    private String timestamp;
    private String status;
    private String exception;

    protected AbstractErrorResponse(final Exception e, final HttpStatus status) {
        this.timestamp = timestampToString(ApplicationTimeUtils.getTimeUTC());
        this.status = status.value() + " " + status.getReasonPhrase();
        this.exception = e.getClass().getName();
    }

    private String timestampToString(LocalDateTime timestamp) {
        String sTimestamp = String.valueOf(timestamp);
        return sTimestamp.substring(0, sTimestamp.length() - 3);
    }
}