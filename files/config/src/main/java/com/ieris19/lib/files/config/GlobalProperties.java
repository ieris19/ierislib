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

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * A class container for properties and other constants in the system. They're defined in
 * <code>properties.properties</code> and access through this class, this ensures that even if the
 * individual values of each property changes, the system should still work as long as the key remains unchanged
 */
public class GlobalProperties extends IerisProperties implements Closeable {
	/**
	 * Singleton instance of the property container
	 */
	private static GlobalProperties instance;

	/**
	 * Static name definition for {@code GlobalProperties}
	 */
	private static String name;

	/**
	 * Static configuration directory for {@code GlobalProperties}
	 */
	private static File configDir;

	static {
		//Default values
		configDir = new File("config");
		name = "properties";
	}

	/**
	 * Initializes the class by reading the properties file. Global properties will take all parameters before
	 * initialization.
	 *
	 * @see #nameProperties(String)
	 * @see #setDirectory(File)
	 */
	private GlobalProperties() {
		super(name, configDir);
	}

	/**
	 * Sets the name for the properties file. This operation cannot be performed once the singleton has been
	 * instantiated.
	 *
	 * @param name of the properties file minus the file extension ".properties"
	 *
	 * @throws IllegalStateException if the object has already been instantiated
	 */

	public static synchronized void nameProperties(String name) throws IllegalStateException {
		if (instance == null)
			GlobalProperties.name = name;
		else
			throw new IllegalStateException("Properties have already been instantiated");
	}

	/**
	 * Sets the directory where the properties will be read from and stored to
	 *
	 * @param dir File object representing the folder that will hold properties
	 *
	 * @throws IllegalStateException if the object has already been instantiated
	 */
	public static synchronized void setDirectory(File dir) throws IllegalStateException {
		if (instance == null)
			GlobalProperties.configDir = dir;
		else
			throw new IllegalStateException("Properties have already been instantiated");
	}

	/**
	 * Returns the only instance of this class that can exist during runtime. The first time it's called, it will create
	 * said instance, but any subsequent call will return the existing instance. If the instance has been previously
	 * closed, it will be treated as the first call again <br><br>
	 *
	 * If settings have not been previously set, it will instantiate a default "properties.properties" in the default
	 * "/config/" directory. <br> Make sure to name them before calling this method if your properties file has a
	 * different path
	 *
	 * @return The instance of this class.
	 *
	 * @see #nameProperties(String) Setting a name for your properties
	 * @see #setDirectory(File) Setting a directory for your properties
	 */
	public static synchronized GlobalProperties getInstance() {
		if (instance == null) {
			instance = new GlobalProperties();
		}
		return instance;
	}

	/**
	 * Closes this stream and releases any system resources associated with it. If the stream is already closed then
	 * invoking this method has no effect.
	 *
	 * @throws IOException if an I/O error occurs
	 */
	@Override public void close() throws IOException {
		try {
			saveProperties();
			instance = null;
		} catch (IOException e) {
			throw new IOException("Could not store the properties, the properties have been deleted", e);
		}
	}
}
