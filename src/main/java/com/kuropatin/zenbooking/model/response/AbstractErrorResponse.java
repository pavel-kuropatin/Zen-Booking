package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.util.ApplicationTimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class AbstractErrorResponse {

    private String timestamp;
    private String status;
    private String exception;

    protected AbstractErrorResponse(final Exception e, final HttpStatus status) {
        this.timestamp = ApplicationTimeUtils.getTimeString();
        this.status = status.value() + " " + status.getReasonPhrase();
        this.exception = e.getClass().getName();
    }
}