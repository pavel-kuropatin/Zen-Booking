package com.kuropatin.zenbooking.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityConstants {

    public static final String X_AUTH_TOKEN = "X-Auth-Token";
    public static final int PASSWORD_ENCODER_STRENGTH = 10;
}