package lib.ieris19.util.properties;

import java.io.File;
import java.io.IOException;

/**
 * Properties that can be dynamically changed. The properties are stored in a file and read from a file for each access.
 * This class is expensive in terms of performance, so it is recommended to use it only for properties that are changed
 * frequently or for properties that are not used frequently but need to be manually changed without interrupting the
 * program.
 */
public class DynamicProperties extends IerisProperties implements AutoCloseable {
	/**
	 * Initializes the class by creating and reading the properties file.
	 *
	 * @param name      the properties file name
	 * @param configDir the properties parent folder
	 */
	public DynamicProperties(String name, File configDir) {
		super(name, configDir);
	}

	/**
	 * Creates properties provided a name and a path
	 *
	 * @param name          the properties file name
	 * @param configDirPath path of the properties parent folder
	 */
	public DynamicProperties(String name, String configDirPath) {
		super(name, configDirPath);
	}

	/**
	 * Creates properties in the default directory
	 *
	 * @param name name of the properties file
	 */
	public DynamicProperties(String name) {
		super(name);
	}

	/**
	 * Creates properties in the specified directory with the default name
	 *
	 * @param configDir the properties parent folder
	 */
	public DynamicProperties(File configDir) {
		super(configDir);
	}

	/**
	 * Creates properties with the default values
	 */
	public DynamicProperties() {
		super();
	}

	/**
	 * Loads properties from the file and promptly runs {@link IerisProperties#getProperty(String)}
	 *
	 * @param key the name of the desired property
	 *
	 * @return the value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 */
	@Override public String getProperty(String key) throws IllegalArgumentException {
		loadProperties();
		return super.getProperty(key);
	}

	/**
	 * Loads properties from the file and promptly runs {@link IerisProperties#modifyProperty(String, String)}
	 *
	 * @param key   name of the property
	 * @param value actual value of the property
	 *
	 * @throws IllegalArgumentException if the new value is the same as the current
	 * @throws IllegalStateException    if the property doesn't exist
	 */
	@Override public synchronized void modifyProperty(String key, String value)
	throws IllegalArgumentException, IllegalStateException {
		loadProperties();
		super.modifyProperty(key, value);
	}

	/**
	 * Calls {@link IerisProperties#createProperty(String, String)} followed by {@link #saveProperties()}
	 *
	 * @param key   name of the property
	 * @param value actual value of the property
	 *
	 * @throws IllegalArgumentException if the property already exists
	 * @throws RuntimeException    if the property file encounters an error while saving
	 */
	@Override public synchronized void createProperty(String key, String value) throws RuntimeException {
		super.createProperty(key, value);
		try {
			saveProperties();
		} catch (IOException e) {
			throw new RuntimeException(
					"Failed to save properties \n" + "Property: \"" + key + "\" with Value: \"" + value + "\"" +
					" will not persist beyond the current session", e);
		}
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
	@Override public byte getPropertyByte(String key) throws IllegalArgumentException, PropertyTypeException {
		loadProperties();
		return super.getPropertyByte(key);
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
	@Override public short getPropertyShort(String key) throws IllegalArgumentException, PropertyTypeException {
		loadProperties();
		return super.getPropertyShort(key);
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
	@Override public int getPropertyInt(String key) throws IllegalArgumentException, PropertyTypeException {
		loadProperties();
		return super.getPropertyInt(key);
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
	@Override public boolean getPropertyBoolean(String key) throws IllegalArgumentException, PropertyTypeException {
		loadProperties();
		return super.getPropertyBoolean(key);
	}

	/**
	 * Saves the properties to the file and promptly disposes of the object
	 */
	@Override public void close() {
		try {
			saveProperties();
		} catch (IOException e) {
			throw new RuntimeException("Failed to save properties", e);
		}
	}
}
