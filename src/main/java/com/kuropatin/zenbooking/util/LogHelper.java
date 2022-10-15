package com.kuropatin.zenbooking.util;

import com.kuropatin.zenbooking.model.request.Request;
import com.kuropatin.zenbooking.model.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogHelper {

    public static void logRequest(final URI uri) {
        log.info("Request URI: {}.", uri);
    }

    public static <T extends Request> void logRequest(final URI uri, final T request) {
        log.info("Request URI: {}. Request body: {}", uri, request);
    }

    public static <T extends Response> void logResponse(final URI uri, final T response) {
        log.info("Response URI: {}. Response body: {}", uri, response);
    }

    public static <T extends Response> void logResponse(final URI uri, final List<T> response) {
        log.info("Response URI: {}. Response body: {}", uri, response);
    }
}