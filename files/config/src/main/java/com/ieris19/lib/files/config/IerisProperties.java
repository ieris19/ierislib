/*
 * Copyright 2021 Ieris19
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

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * A class container for properties and other constants in the system. They're defined in
 * <code>properties.properties</code> and access through this class, this ensures that even if the
 * individual values of each property, the system should still work as long as the key remains unchanged
 */
public abstract class IerisProperties implements AutoCloseable {
	/**
	 * Name of the properties file. It's full path is from the working directory: <br>
	 * "{@code /configDirectory/name.properties}"
	 */
	private final String name;
	/**
	 * Directory for configuration files and home of the properties file. It will be {@code /config/} by default
	 */
	private final File configDir;
	/**
	 * A hashtable consisting of {@link String} keys and <code>String</code> values that define the properties and
	 * constants to be used by the application
	 */
	private final Properties properties;

	/**
	 * Initializes the class by reading the properties file. The constructor is not public as this should only be
	 * instantiated through {@link GlobalProperties} or {@link DynamicProperties} wrappers
	 *
	 * @param name      the properties file name
	 * @param configDir the properties parent folder
	 */
	protected IerisProperties(String name, File configDir) {
		this.name = name;
		if (!configDir.mkdir()) {
			if (!configDir.isDirectory()) {
				throw new IllegalArgumentException(
						"You're trying to create a directory, but a file with the same name already exists");
			}
		}
		this.configDir = configDir;
		this.properties = new Properties();
		loadProperties();
	}

	/**
	 * Creates properties provided a name and a path
	 *
	 * @param name          the properties file name
	 * @param configDirPath path of the properties parent folder
	 */
	protected IerisProperties(String name, String configDirPath) {
		this(name, new File(configDirPath));
	}

	/**
	 * Creates properties in the default directory
	 *
	 * @param name name of the properties file
	 */
	protected IerisProperties(String name) {
		this(name, "config");
	}

	/**
	 * Creates properties in the specified directory with the default name
	 *
	 * @param configDir the properties parent folder
	 */
	protected IerisProperties(File configDir) {
		this("properties", configDir);
	}

	/**
	 * Creates properties with the default values
	 */
	protected IerisProperties() {
		this("properties", "config");
	}

	/**
	 * Loads the file values into the {@link Properties} object
	 */
	protected void loadProperties() {
		try (InputStream input = new FileInputStream(getPropertyFile())) {
			this.properties.load(input);
		} catch (IOException exception) {
			throw new IllegalStateException("Properties could not be loaded", exception);
		}
	}

	/**
	 * Creates an empty Properties file for the application. This is used when the application is run without the basic
	 * set of configuration options
	 */
	public void createPropertiesFile(String[] keys, String[] defaultValues) {
		if (keys.length == 0) {
			return;
		}
		try (FileWriter fileWriter = new FileWriter(getPropertyFile())) {
			if (keys.length < 1) {
				throw new IndexOutOfBoundsException("There must be at least 1 key");
			}
			if (keys.length == defaultValues.length) {
				for (int i = 0; i < keys.length; i++) {
					fileWriter.write(keys[i] + "=" + defaultValues[i] + '\n');
				}
			}
			if (keys.length > defaultValues.length) {
				for (int i = 0; i < defaultValues.length; i++) {
					fileWriter.write(keys[i] + "=" + defaultValues[i] + '\n');
				}
				for (int i = defaultValues.length; i < keys.length; i++) {
					fileWriter.write(keys[i] + "=" + '\n');
				}
			} else {
				for (String key : keys) {
					fileWriter.write(key + "=null\n");
				}
			}
		} catch (IOException fileException) {
			throw new IllegalStateException("The properties file is inaccessible or an error has occurred", fileException);
		} catch (IndexOutOfBoundsException arrayException) {
			throw new IllegalArgumentException("One of the provided arrays has insufficient arguments", arrayException);
		} catch (NullPointerException nullException) {
			throw new IllegalArgumentException("One of the provided arrays is null", nullException);
		} catch (Exception unexpectedException) {
			throw new IllegalStateException("An unexpected error has occurred", unexpectedException);
		}
	}

	/**
	 * If the properties file is not set up correctly, this method will create a default properties file and continue the
	 * execution of the application. This method should only be called if the properties file is not set up correctly. If
	 * the application can't continue after failing to read a property, then the application should throw an exception.
	 */
	public void createPropertiesFile(String[][] defaultProperties) {
		String[] keys = new String[defaultProperties.length];
		String[] values = new String[defaultProperties.length];
		for (int i = 0; i < defaultProperties.length; i++) {
			String key = defaultProperties[i][0];
			String value = defaultProperties[i][1];
			if (key == null) {
				throw new IllegalArgumentException("One of the keys is null");
			}
			if (value == null) {
				value = "null";
			}
			keys[i] = key;
			values[i] = value;
		}
		createPropertiesFile(keys, values);
	}

	/**
	 * If the properties file is not set up correctly, this method will create a default properties file and continue the
	 * execution of the application. This method should only be called if the properties file is not set up correctly. If
	 * the application can't continue after failing to read a property, then the application should throw an exception.
	 */
	public void createDefaultProperties(String[][] defaultProperties) {
		for (String[] property : defaultProperties) {
			try {
				this.createProperty(property[0], property[1]);
			} catch (IllegalArgumentException ignored) {
			}
		}
		try {
			this.saveProperties();
		} catch (IOException fileException) {
			throw new IllegalStateException("The properties file is inaccessible or an error has occurred", fileException);
		}
	}

	/**
	 * Name of the properties file. The full name of the file is: <br> "{@code name.properties}"
	 *
	 * @return the name of the properties file
	 */
	protected String getName() {
		return name;
	}

	/**
	 * Prepares a property file with the appropriate directory and name
	 *
	 * @return the Properties file being used
	 *
	 * @throws IOException if an I/O error occurs
	 */
	protected File getPropertyFile() throws IOException {
		String fileName = name + ".properties";
		File configFile = new File(configDir, fileName);
		if (!configFile.createNewFile()) {
			if (!configFile.exists()) {
				throw new IOException("The properties file could not be created");
			}
		}
		return configFile;
	}

	/**
	 * A copy of the Properties object wrapped by this library. This is to prevent the user from modifying the properties
	 * directly and to ensure that the properties are always updated through this class' methods and its verification
	 * methods
	 *
	 * @return a copy of the Properties object
	 */
	public Properties getProperties() {
		Properties temp = (Properties) Map.copyOf(this.properties);
		return properties;
	}

	/**
	 * Retrieves the value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 */
	public String getProperty(String key) throws IllegalArgumentException {
		String propertyValue;
		synchronized (properties) {
			propertyValue = properties.getProperty(key);
		}
		if (propertyValue == null) {
			throw new IllegalArgumentException("Invalid Property Name");
		}
		return propertyValue;
	}

	/**
	 * Inserts a key/value pair as a property. This method should only be called by another method in order to perform any
	 * logic checks necessary
	 *
	 * @param key   name of the property
	 * @param value actual value of the property
	 */
	protected void setProperties(String key, String value) {
		synchronized (properties) {
			properties.put(key, value);
		}
	}

	/**
	 * Modifies a property, it will throw an exception if the property doesn't exist. If the new value is the same as the
	 * current, it will also throw an exception
	 *
	 * @param key   name of the property
	 * @param value actual value of the property
	 *
	 * @throws IllegalArgumentException if the new value is the same as the current
	 * @throws IllegalStateException    if the property doesn't exist
	 */
	public synchronized void modifyProperty(String key, String value)
	throws IllegalArgumentException, IllegalStateException {
		String oldValue = getProperty(key);
		if (oldValue == null) {
			throw new IllegalStateException("Property doesn't exist");
		}
		if (oldValue.equals(value)) {
			throw new IllegalArgumentException("Property value is already " + value);
		}
		setProperties(key, value);
	}

	/**
	 * Creates a property, it will throw an exception if the property already exists.
	 *
	 * @param key   name of the property
	 * @param value actual value of the property
	 *
	 * @throws IllegalArgumentException if the property already exists
	 */
	public synchronized void createProperty(String key, String value) throws IllegalArgumentException {
		String property = properties.getProperty(key);
		if (property == null) {
			setProperties(key, value);
		} else {
			throw new IllegalArgumentException("Property already exists");
		}
	}

	/**
	 * Deletes a property, it will throw an exception if the property doesn't exist.
	 *
	 * @param key name of the property
	 *
	 * @throws IllegalArgumentException if the property doesn't exist
	 */
	public synchronized void deleteProperty(String key) throws IllegalArgumentException {
		String property = properties.getProperty(key);
		if (property == null) {
			throw new IllegalArgumentException("Property doesn't exist");
		}
		properties.remove(key);
	}

	/**
	 * Retrieves the single Byte value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the byte value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 * @throws PropertyTypeException    if the property is not a Byte
	 */
	public byte getPropertyByte(String key) throws IllegalArgumentException, PropertyTypeException {
		try {
			return Byte.parseByte(getProperty(key));
		} catch (NumberFormatException e) {
			throw new PropertyTypeException(properties, key, "Byte");
		}
	}

	/**
	 * Retrieves the Short Integer value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the short value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 * @throws PropertyTypeException    if the property is not a Short
	 */
	public short getPropertyShort(String key) throws IllegalArgumentException, PropertyTypeException {
		try {
			return Short.parseShort(getProperty(key));
		} catch (NumberFormatException e) {
			throw new PropertyTypeException(properties, key, "Short");
		}
	}

	/**
	 * Retrieves the Integer value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the integer value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 * @throws PropertyTypeException    if the property is not an Integer
	 */
	public int getPropertyInt(String key) throws IllegalArgumentException, PropertyTypeException {
		try {
			return Integer.parseInt(getProperty(key));
		} catch (NumberFormatException e) {
			throw new PropertyTypeException(properties, key, "Integer");
		}
	}

	/**
	 * Retrieves the Long Integer value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the long value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 * @throws PropertyTypeException    if the property is not a Long
	 */
	public long getPropertyLong(String key) throws IllegalArgumentException, PropertyTypeException {
		try {
			return Long.parseLong(getProperty(key));
		} catch (NumberFormatException e) {
			throw new PropertyTypeException(properties, key, "Long");
		}
	}

	/**
	 * Retrieves the Floating point value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the float value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 * @throws PropertyTypeException    if the property is not a Float
	 */
	public float getPropertyFloat(String key) throws IllegalArgumentException, PropertyTypeException {
		try {
			return Float.parseFloat(getProperty(key));
		} catch (NumberFormatException e) {
			throw new PropertyTypeException(properties, key, "Float");
		}
	}

	/**
	 * Retrieves the Double precision floating point value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the double value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 * @throws PropertyTypeException    if the property is not a Double
	 */
	public double getPropertyDouble(String key) throws IllegalArgumentException, PropertyTypeException {
		try {
			return Double.parseDouble(getProperty(key));
		} catch (NumberFormatException e) {
			throw new PropertyTypeException(properties, key, "Double");
		}
	}

	/**
	 * Retrieves the Character value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the char value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 * @throws PropertyTypeException    if the property is not a Character
	 */
	public char getPropertyCharacter(String key) throws IllegalArgumentException, PropertyTypeException {
		String value = getProperty(key);
		if (value.length() != 1 || value.matches("\\d")) {
			throw new PropertyTypeException(properties, key, "Character");
		}
		return value.charAt(0);
	}

	/**
	 * Retrieves the boolean value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the boolean value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 * @throws PropertyTypeException    if the property is not a boolean
	 */
	public boolean getPropertyBoolean(String key) throws IllegalArgumentException, PropertyTypeException {
		String value = getProperty(key);
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1"))
			return true;
		if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("0"))
			return false;
		else
			throw new PropertyTypeException(properties, key, "Boolean");
	}

	/**
	 * Writes out the properties into the property file where they were read from
	 *
	 * @throws IOException if an I/O error occurs
	 */
	public void saveProperties() throws IOException {
		try {
			properties.store(new FileWriter(getPropertyFile()), name.substring(0, 1).toUpperCase() + name.substring(1));
		} catch (IOException e) {
			throw new IOException("Could not save the properties", e);
		}
	}
}
