package com.ieris19.lib.files.config.base;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.files.config.error.IncorrectPropertyStatusException;
import com.ieris19.lib.files.config.error.IncorrectPropertyTypeException;

import java.net.URI;
import java.util.Optional;

public abstract class BaseConfigManager implements ConfigManager {
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
