package com.ieris19.lib.files.config.api;

import java.net.URI;

public interface FileConfigManager extends ConfigManager {
    /**
     * Loads the properties from file, the file should be specified in the
     * constructor of the implementing class, as this interface does not have
     * a method to set the file path
     */
    void loadProperties();

    /**
     * Returns the path to the properties file
     *
     * @return the path to the properties file
     */
    URI getPropertiesPath();

    /**
     * Saves the properties to file
     */
    void save();
}
