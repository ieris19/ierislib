package com.ieris19.lib.common.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class NullHelper {
    public static <T> T getOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    @SafeVarargs
    public static <T> T coalesce(T... values) {
        for (T value : values) {
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public static <T> T requireNonNull(T value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return value;
    }

    public static <T> T checkRequiredFields(T value) {
        if (value == null) {
            throw new NullPointerException();
        }
        List<Field> missingField = Arrays.stream(value.getClass().getFields())
                .filter(field -> field.isAnnotationPresent(RequiredField.class))
                .filter(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.get(value) == null) {
                            return true;
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    return false;
                }).toList();
        if (!missingField.isEmpty()) {
            StringBuilder message = new StringBuilder("The object is missing required fields: ");
            missingField.forEach(field -> message.append(field.getName()).append("\n"));
            throw new NullPointerException(message.toString());
        }
        return value;
    }
}
