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

package com.ieris19.lib.files.config.error;

/**
 * A type of exception thrown when a property was expected to be of a certain type, but it was not
 * possible to convert it into such
 */
public class IncorrectPropertyTypeException extends RuntimeException {
    public IncorrectPropertyTypeException(String message) {
        super(message);
    }

    public IncorrectPropertyTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
