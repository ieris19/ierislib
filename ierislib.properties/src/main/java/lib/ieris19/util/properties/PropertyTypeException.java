package lib.ieris19.util.properties;

import java.util.Properties;

/**
 * A type of exception that is thrown when a property is not of the expected type when trying to retrieve it as a
 * specific type differing from String, which is the type that properties are stored as
 */
public class PropertyTypeException extends RuntimeException {
	/**
	 * Constructs a {@code PropertyTypeException} with a detail message built given the current
	 *
	 * @param properties object whose property was being retrieved
	 * @param key        of the desired property
	 * @param castType   type to which the property was trying to be parsed into
	 */
	public PropertyTypeException(Properties properties, String key, String castType) {
		super(getDetailMessage(properties, key, castType));
	}

	/**
	 * Constructs a {@code PropertyTypeException} with a detail message built given the current properties, key and cast
	 * type
	 *
	 * @param properties properties file that was being retrieved
	 * @param key        key of the desired property
	 * @param castType   type to which the property was trying to be parsed into
	 * @param cause      exception that caused this exception
	 */
	public PropertyTypeException(Properties properties, String key, String castType, Throwable cause) {
		super(getDetailMessage(properties, key, castType), cause);
	}

	/**
	 * Constructs a {@code PropertyTypeException} with a detail message built given the current properties, key and cast
	 * type
	 *
	 * @param properties properties file that was being retrieved
	 * @param key        key of the desired property
	 * @param castType   type to which the property was trying to be parsed into
	 *
	 * @return detail message built given the current properties, key and cast type
	 */
	private static String getDetailMessage(Properties properties, String key, String castType) {
		return "Property cannot be returned, incompatible type requested: \n" + "Property: " + key + "with value: " +
					 properties.getProperty(key) + " is not " + (castType.substring(0, 1).matches("[aeiou]") ? "an" : "a") +
					 castType;
	}
}
