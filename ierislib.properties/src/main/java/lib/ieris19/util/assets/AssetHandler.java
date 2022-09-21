package lib.ieris19.util.assets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

/**
 * Class that handles the loading of assets from the assets folder
 */
public class AssetHandler implements AutoCloseable {
	/**
	 * The path to the assets folder
	 */
	private static File assetRoot;
	/**
	 * The map of subdirectories of assets
	 */
	private static final HashMap<String, File> subdirectories = new HashMap<>();

	static {
		assetRoot = new File("assets");
	}

	/**
	 * Sets the subdirectory for the assets
	 */
	private File assetFolder;

	/**
	 * Sets the path to the assets folder
	 *
	 * @param path the path to the assets folder
	 */
	public static void setAssetRoot(String path) throws IllegalStateException {
		if (subdirectories.isEmpty())
			assetRoot = new File(path);
		else
			throw new IllegalStateException("Cannot change asset folder after assets have been loaded");
	}

	/**
	 * Returns or creates an asset handler for the specified subdirectory
	 *
	 * @param key the name of the subdirectory
	 *
	 * @return the asset handler for the subdirectory
	 */
	public static synchronized AssetHandler getInstance(String key) {
		File assetFolder;
		if (subdirectories.containsKey(key)) {
			assetFolder = subdirectories.get(key);
		} else {
			assetFolder = new File(assetRoot, key);
			if (!assetFolder.exists()) {
				assetFolder.mkdir();
			}
			if (!assetFolder.isDirectory()) {
				throw new IllegalArgumentException("Asset type is not a directory");
			}
			subdirectories.put(key, assetFolder);
		}
		return new AssetHandler(assetFolder);
	}

	/**
	 * Gets the URL of the specified asset relative to the calling class resource folder. This means that it will try to
	 * look for the asset in the same folder as the calling class file, to compile an asset into the calling class
	 * resource folder you must use a resources folder mirroring your package structure, and place the asset in the same
	 * package as the calling class
	 *
	 * @param name the name of the asset
	 *
	 * @return the URL of the asset, null if the caller class cannot be found, or the asset cannot be found
	 */
	public static URL getResource(String name) {
		try {
			Class<?> resourceCaller = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
			return resourceCaller.getResource(name);
		} catch (ClassNotFoundException ignored) {
			return null;
		}
	}

	/**
	 * Gets the input stream of the specified asset relative to the calling class resource folder. This means that it will
	 * try to look for the asset in the same folder as the calling class file, to compile an asset into the calling class
	 * resource folder you must use a resources folder mirroring your package structure, and place the asset in the same
	 * package as the calling class
	 *
	 * @param name the name of the asset
	 *
	 * @return the input stream of the asset, null if the caller class cannot be found, or the asset cannot be found
	 */
	public static InputStream getResourceAsString(String name) {
		try {
			Class<?> resourceCaller = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
			return resourceCaller.getResourceAsStream(name);
		} catch (ClassNotFoundException ignored) {
			return null;
		}
	}

	/**
	 * Creates a new AssetHandler for the specified asset type subdirectory
	 *
	 * @param assetFolder the subdirectory from where to load assets
	 */
	public AssetHandler(File assetFolder) {
		this.assetFolder = assetFolder;
	}

	/**
	 * Gets the file from the assets folder
	 *
	 * @param name The name to the file
	 *
	 * @return The file in the specified path
	 */
	public File getAsset(String name) {
		return new File(assetRoot, name);
	}

	/**
	 * Gets the file from the assets folder and opens it as a {@code FileInputStream}
	 *
	 * @param name The name to the file
	 *
	 * @return An input stream of the file binary contents
	 *
	 * @throws FileNotFoundException if the file does not exist
	 */
	public FileInputStream getAssetAsStream(String name) throws FileNotFoundException {
		return new FileInputStream(new File(assetFolder, name));
	}

	/**
	 * Gets all the files from the assets folder
	 *
	 * @return All the files from the specified path
	 *
	 * @throws IllegalArgumentException if the path is not a folder
	 */
	public File[] getAssets() throws IllegalArgumentException {
		return assetFolder.listFiles();
	}

	/**
	 * Closes this resource, relinquishing any underlying resources. This method is invoked automatically on objects
	 * managed by the {@code try}-with-resources statement.
	 *
	 * @throws Exception if this resource cannot be closed
	 */
	@Override public void close() {
		assetFolder = null;
	}
}
