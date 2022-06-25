package lib.ieris19.util.properties;

import java.util.Properties;

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

	public PropertyTypeException(Properties properties, String key, String castType,
															 Throwable cause) {
		super(getDetailMessage(properties, key, castType), cause);
	}

	private static String getDetailMessage(Properties properties, String key, String castType) {
		return "Property cannot be returned, incompatible type requested: \n" + "Property: " + key
					 + "with value: " + properties.getProperty(key) + " is not a " + castType;
	}
}
