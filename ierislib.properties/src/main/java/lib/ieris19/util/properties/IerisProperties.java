package lib.ieris19.util.properties;

import java.io.*;
import java.util.Properties;

/**
 * A class container for properties and other constants in the system. They're defined in
 * <code>properties.properties</code> and access through this class, this ensures that even if the
 * individual values of each property, the system should still work as long as the key remains
 * unchanged
 */
public class IerisProperties implements Closeable {
	/**
	 * Singleton instance of the property container
	 */
	private static IerisProperties instance;

	private static String name = "properties";

	/**
	 * A hashtable consisting of {@link String} keys and <code>String</code> values that define the
	 * properties and constants to be used by the application
	 */
	private Properties properties;

	/**
	 * Initializes the class by reading the properties file
	 */
	private IerisProperties() {
		properties = new Properties();
		try (InputStream input = new FileInputStream(name + ".properties")) {
			properties.load(input);
		} catch (IOException exception) {
			throw new IllegalStateException("Properties could not be loaded", exception);
		}
	}

	/**
	 * Sets the name for the properties file. This operation cannot be performed once the singleton
	 * has been instantiated.
	 *
	 * @param name of the properties file minus the file extension ".properties"
	 */
	public void nameProperties(String name) {
		if (instance == null)
			IerisProperties.name = name;
		else
			throw new IllegalStateException("Properties have already been instantiated");
	}

	/**
	 * Returns the only instance of this class that can exist during runtime. The first time it's
	 * called, it will create said instance, but any subsequent call will return the existing
	 * instance. If the instance has been previously closed, it will be treated as the first call
	 * again <br><br>
	 *
	 * If properties have not been named before it will instantiate a default "properties.properties".
	 * Make sure to name them before calling this method if your properties file has a different name
	 *
	 * @return The instance of this class.
	 */
	public static synchronized IerisProperties getInstance() {
		if (instance == null) {
			instance = new IerisProperties();
		}
		return instance;
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
	public synchronized String getProperty(String key) {
		String property = properties.getProperty(key);
		if (property == null) {
			throw new IllegalArgumentException("Invalid Property Name");
		}
		return property;
	}

	/**
	 * Inserts a key/value pair as a property. This method should only be called by another method in
	 * order to perform any logic checks necessary
	 *
	 * @param key   name of the property
	 * @param value actual value of the property
	 */
	private synchronized void setProperties(String key, String value) {
		properties.put(key, value);
	}

	/**
	 * Modifies a property, it will throw an exception if the property doesn't exist. If the new value
	 * is the same as the current, it will not change it
	 *
	 * @param key   name of the property
	 * @param value actual value of the property
	 */
	public synchronized void modifyProperty(String key, String value) {
		try {
			String oldValue = properties.getProperty(key);
			if (oldValue != null) {
				if (!oldValue.equals(value)) {
					setProperties(key, value);
				}
			} else {
				throw new IllegalStateException("Property doesn't exist");
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Property doesn't exist");
		}
	}

	/**
	 * Creates a property, it will throw an exception if the property already exists.
	 *
	 * @param key   name of the property
	 * @param value actual value of the property
	 */
	public synchronized void createProperty(String key, String value) {
		String property = properties.getProperty(key);
		if (property == null) {
			setProperties(key, value);
		}
		throw new IllegalArgumentException("Property already exists");
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
	public byte getPropertyByte(String key) {
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
	public short getPropertyShort(String key) {
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
	public int getPropertyInt(String key) {
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
	public boolean getPropertyBoolean(String key) {
		String value = getProperty(key);
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1"))
			return true;
		if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("0"))
			return false;
		else
			throw new PropertyTypeException(properties, key, "Boolean");
	}

	/**
	 * Closes this stream and releases any system resources associated with it. If the stream is
	 * already closed then invoking this method has no effect.
	 *
	 * @throws IOException if an I/O error occurs
	 */
	@Override public void close() throws IOException {
		try {
			if (instance != null) {
				properties.store(new FileWriter("properties.properties"), name);
			}
			instance = null;
		} catch (IOException e) {
			throw new IOException("Could not store the properties, the properties have been deleted", e);
		}
	}
}
