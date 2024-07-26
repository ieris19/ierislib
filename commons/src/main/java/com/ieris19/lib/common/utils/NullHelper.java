/*
 * Copyright 2024 Ieris19
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

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
