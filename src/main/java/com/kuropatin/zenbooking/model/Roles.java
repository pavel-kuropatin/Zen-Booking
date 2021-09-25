package com.kuropatin.zenbooking.model;

public enum Roles {
    ROLE_ADMIN("ADMIN"),
    ROLE_MODER("MODER"),
    ROLE_USER("USER");

    public final String label;

    Roles(String label) {
        this.label = label;
    }
}