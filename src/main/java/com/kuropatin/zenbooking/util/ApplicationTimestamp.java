package com.kuropatin.zenbooking.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationTimestamp {

    public static Timestamp getTimestampUTC() {
        return Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC));
    }

    public static Timestamp getZonedTimestamp(ZoneOffset zoneOffset) {
        return Timestamp.valueOf(LocalDateTime.now(zoneOffset));
    }
}