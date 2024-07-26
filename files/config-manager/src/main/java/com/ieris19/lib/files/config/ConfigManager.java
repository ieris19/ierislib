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

/**
 * A facade interface for configuration classes. This interface is meant to be
 * implemented by classes that provide configuration management.
 */
public interface ConfigManager {
    /**
     * Loads the properties from file
     */
    void loadProperties();

    /**
     * Returns the path to the properties file
     *
     * @return the path to the properties file
     */
    URI getPropertiesPath();

    /**
     * Returns the value of the property with the given key
     *
     * @param key the key of the property
     * @return the value of the property
     */
    Optional<String> getProperty(String key);

    /**
     * Sets the value of the property with the given key. If the key does not exist,
     * it will be added to the properties
     *
     * @param key   the key of the property
     * @param value the value of the property
     */
    void setProperty(String key, String value);

    /**
     * Modifies the value of the property with an already existing key. If the key
     * does not exist, it will throw an exception
     *
     * @param key   the key of the property
     * @param value the value of the property
     */
    void modifyProperty(String key, String value) throws IncorrectPropertyStatusException;

    /**
     * Modifies the value of the property with a new key. If the key
     * already exists, it will throw an exception
     *
     * @param key   the key of the property
     * @param value the value of the property
     */
    void createProperty(String key, String value) throws IncorrectPropertyStatusException;

    /**
     * Deletes the property with the given key. If the key does not exist, nothing
     * will happen.
     * throw an exception
     *
     * @param key the key of the property
     */
    void deleteProperty(String key);

    /**
     * Checks if the property with the given key is present. If it is not, it will
     * throw an exception.
     *
     * @param key the key of the property
     * @return true if the property is present, false otherwise
     */
    boolean propertyPresent(String key);

    /**
     * Saves the properties to file
     */
    void save();

    /**
     * Returns the value of the property with the given key as a byte
     *
     * @param key the key of the property
     * @return the value of the property as a byte
     * @throws IncorrectPropertyTypeException if the property is not a byte
     */
    Optional<Byte> getByteProperty(String key) throws IncorrectPropertyTypeException;

    /**
     * Returns the value of the property with the given key as a short
     *
     * @param key the key of the property
     * @return the value of the property as a short
     * @throws IncorrectPropertyTypeException if the property is not a short
     */
    Optional<Short> getShortProperty(String key) throws IncorrectPropertyTypeException;

    /**
     * Returns the value of the property with the given key as an integer
     *
     * @param key the key of the property
     * @return the value of the property as an integer
     * @throws IncorrectPropertyTypeException if the property is not an integer
     */
    Optional<Integer> getIntProperty(String key) throws IncorrectPropertyTypeException;

    /**
     * Returns the value of the property with the given key as a long
     *
     * @param key the key of the property
     * @return the value of the property as a long
     * @throws IncorrectPropertyTypeException if the property is not a long
     */
    Optional<Long> getLongProperty(String key) throws IncorrectPropertyTypeException;

    /**
     * Returns the value of the property with the given key as a float
     *
     * @param key the key of the property
     * @return the value of the property as a float
     * @throws IncorrectPropertyTypeException if the property is not a float
     */
    Optional<Float> getFloatProperty(String key) throws IncorrectPropertyTypeException;

    /**
     * Returns the value of the property with the given key as a double
     *
     * @param key the key of the property
     * @return the value of the property as a double
     * @throws IncorrectPropertyTypeException if the property is not a double
     */
    Optional<Double> getDoubleProperty(String key) throws IncorrectPropertyTypeException;

    /**
     * Returns the value of the property with the given key as a boolean
     *
     * @param key the key of the property
     * @return the value of the property as a boolean
     * @throws IncorrectPropertyTypeException if the property is not a boolean
     */
    Optional<Boolean> getBooleanProperty(String key) throws IncorrectPropertyTypeException;

    /**
     * Returns the value of the property with the given key as a character
     *
     * @param key the key of the property
     * @return the value of the property as a character
     * @throws IncorrectPropertyTypeException if the property is not a character
     */
    Optional<Character> getCharProperty(String key);
}
