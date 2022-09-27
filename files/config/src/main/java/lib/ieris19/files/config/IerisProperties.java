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

package lib.ieris19.files.config;

import java.io.*;
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
		this.configDir = configDir;
		this.configDir.mkdir();
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
		configFile.createNewFile();
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
		Properties temp = new Properties();
		this.properties.forEach((key, value) -> properties.put((String) key, (String) value));
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
	 * Retrieves the byte value from a property based on the provided key
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
	 * Retrieves the short value from a property based on the provided key
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
	 * Retrieves the integer value from a property based on the provided key
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
			throw new IOException("Could not store the properties", e);
		}
	}
}
