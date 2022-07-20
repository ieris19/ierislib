package lib.ieris19.util.properties;

import java.io.*;
import java.util.Properties;

/**
 * A class container for properties and other constants in the system. They're defined in
 * <code>properties.properties</code> and access through this class, this ensures that even if the
 * individual values of each property, the system should still work as long as the key remains unchanged
 */
public class IerisProperties {
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
	private Properties properties;

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
		this.properties = new Properties();
		try (InputStream input = new FileInputStream(getPropertyFile())) {
			this.properties.load(input);
		} catch (IOException exception) {
			throw new IllegalStateException("Properties could not be loaded", exception);
		}
	}

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
	 * Retrieves the value from a property based on the provided key
	 *
	 * @param key the name of the desired property
	 *
	 * @return the value of the property corresponding to the key
	 *
	 * @throws IllegalArgumentException if the property does not exist
	 */
	public String getProperty(String key) {
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
	public void setProperties(String key, String value) {
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
	 */
	public synchronized void modifyProperty(String key, String value) {
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

	public void saveProperties() throws IOException {
		try {
			properties.store(new FileWriter(getPropertyFile()), name);
		} catch (IOException e) {
			throw new IOException("Could not store the properties", e);
		}
	}
}
