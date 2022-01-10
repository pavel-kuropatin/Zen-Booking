package com.kuropatin.zenbooking.model;

public enum OrderStatus {

    ACTIVE_NOT_ACCEPTED("NOT ACCEPTED"),
    ACTIVE_ACCEPTED("ACCEPTED"),
    FINISHED("FINISHED"),
    CANCELLED("CANCELLED"),
    DECLINED("DECLINED"),
    STATUS_UNKNOWN("STATUS UNKNOWN");

    public final String label;

    OrderStatus(String label) {
        this.label = label;
    }
}