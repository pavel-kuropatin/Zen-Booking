package com.kuropatin.zenbooking.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheNames {

    public static final String ORDER = "order";
    public static final String PROPERTY_IMAGE = "propertyImage";
    public static final String PROPERTY = "property";
    public static final String REVIEW = "review";
    public static final String USER = "user";
    public static final String BOOLEAN = "boolean";
    public static final String DOUBLE = "double";

    public static String[] getCacheNames() {
        try {
            final Field[] fields = Class.forName(CacheNames.class.getName()).getDeclaredFields();
            final String[] cacheNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                cacheNames[i] = (String) fields[i].get(fields[i].getName());
            }
            return cacheNames;
        } catch (ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}