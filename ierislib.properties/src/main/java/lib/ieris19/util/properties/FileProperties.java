package lib.ieris19.util.properties;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Properties that are read from a specific type. Every time a {@link FileProperties} object is created, the properties
 * are read from the file. Whenever the properties stop being used, the {@link #close()} method should be called to
 * release the resources and save the properties to the file. This class is ideally used in {@code try-with-resources}
 * blocks.
 */
public class FileProperties extends IerisProperties implements Closeable {
	/**
	 * Multiton instance Hashmap
	 */
	private static final HashMap<String, FileProperties> instances;

	/**
	 * Static configuration directory for {@code FileProperties}
	 */
	private static File configDir;

	static {
		instances = new HashMap<>();
		configDir = new File("config");
	}

	/**
	 * Creates a {@code FileProperties} instance for the specified name
	 *
	 * @param key the name of the properties file
	 */
	private FileProperties(String key) {
		super(key, configDir);
	}

	/**
	 * Sets the directory where the properties will be read from and stored to
	 *
	 * @param dir File object representing the folder that will hold properties
	 * @throws IllegalStateException if a file has already been loaded by this class
	 */
	public static synchronized void setDirectory(File dir) throws IllegalStateException {
		if (instances.isEmpty())
			configDir = dir;
		else
			throw new IllegalStateException("Properties directory is already in use");
	}

	/**
	 * Returns an instance of this class that can exist during runtime. The first time it's called for a given key, it
	 * will create said instance, but any subsequent call will return the existing instance. If the instance has been
	 * previously closed, it will be treated as the first call again <br><br>
	 *
	 * If settings have not been previously set, it will instantiate in the default "/config/" directory. <br> Make sure
	 * to configure the path before calling this method if your properties file has a different path
	 *
	 * @return The instance of this class.
	 */
	public static synchronized FileProperties getInstance(String key) {
		FileProperties instance = instances.get(key);
		if (instance == null) {
			instance = new FileProperties(key);
			instances.put(key, instance);
		}
		return (FileProperties) instance;
	}

	/**
	 * Closes this stream and releases any system resources associated with it. If the stream is already closed then
	 * invoking this method has no effect.
	 *
	 * @throws IOException if an I/O error occurs
	 */
	@Override public void close() throws IOException {
		try {
			this.saveProperties();
			instances.remove(this.getName());
		} catch (IOException e) {
			throw new IOException("Could not store the properties, the properties have been deleted", e);
		}
	}
}
