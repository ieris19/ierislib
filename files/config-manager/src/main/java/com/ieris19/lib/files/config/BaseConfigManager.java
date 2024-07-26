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

package com.ieris19.lib.files.config;

import com.ieris19.lib.files.config.error.IncorrectPropertyStatusException;
import com.ieris19.lib.files.config.error.IncorrectPropertyTypeException;

import java.net.URI;
import java.util.Optional;

public abstract class BaseConfigManager implements ConfigManager {
    URI path;

    public BaseConfigManager(URI path) {
        this.path = path;
    }

    @Override
    public URI getPropertiesPath() {
        return path;
    }

    @Override
    public void modifyProperty(String key, String value) throws IncorrectPropertyStatusException {
        if (this.propertyPresent(key)) {
            this.setProperty(key, value);
        } else {
            throw new IncorrectPropertyStatusException("Property does not exist");
        }
    }

    @Override
    public void createProperty(String key, String value) throws IncorrectPropertyStatusException {
        if (!this.propertyPresent(key)) {
            this.setProperty(key, value);
        } else {
            throw new IncorrectPropertyStatusException("Property already exists");
        }
    }

    @Override
    public Optional<Byte> getByteProperty(String key) throws IncorrectPropertyTypeException {
        try {
            return Optional.of(Byte.parseByte(getProperty(key).orElse("0")));
        } catch (NumberFormatException e) {
            throw new IncorrectPropertyTypeException("Property is not a byte");
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Short> getShortProperty(String key) throws IncorrectPropertyTypeException {
        try {
            return Optional.of(Short.parseShort(getProperty(key).orElseThrow(NullPointerException::new)));
        } catch (NumberFormatException e) {
            throw new IncorrectPropertyTypeException("Property is not a byte");
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getIntProperty(String key) throws IncorrectPropertyTypeException {
        try {
            return Optional.of(Integer.parseInt(getProperty(key).orElseThrow(NullPointerException::new)));
        } catch (NumberFormatException e) {
            throw new IncorrectPropertyTypeException("Property is not a byte");
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> getLongProperty(String key) throws IncorrectPropertyTypeException {
        try {
            return Optional.of(Long.parseLong(getProperty(key).orElseThrow(NullPointerException::new)));
        } catch (NumberFormatException e) {
            throw new IncorrectPropertyTypeException("Property is not a byte");
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Float> getFloatProperty(String key) throws IncorrectPropertyTypeException {
        try {
            return Optional.of(Float.parseFloat(getProperty(key).orElseThrow(NullPointerException::new)));
        } catch (NumberFormatException e) {
            throw new IncorrectPropertyTypeException("Property is not a byte");
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Double> getDoubleProperty(String key) throws IncorrectPropertyTypeException {
        try {
            return Optional.of(Double.parseDouble(getProperty(key).orElseThrow(NullPointerException::new)));
        } catch (NumberFormatException e) {
            throw new IncorrectPropertyTypeException("Property is not a byte");
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> getBooleanProperty(String key) throws IncorrectPropertyTypeException {
        try {
            String value = getProperty(key).orElseThrow(NullPointerException::new);
            return switch (value.toLowerCase()) {
                case "true", "1" -> Optional.of(true);
                case "false", "0" -> Optional.of(false);
                default ->
                        throw new IncorrectPropertyTypeException("Property is not a boolean");
            };
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Character> getCharProperty(String key) {
        try {
            String value = getProperty(key).orElseThrow(NullPointerException::new);
            if (value.length() != 1) {
                throw new IncorrectPropertyTypeException("Property is not a character");
            }
            return Optional.of(value.charAt(0));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }
}
