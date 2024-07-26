/*
 * Copyright 2024 Ieris19
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

package com.ieris19.lib.files.assets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Class that handles the loading of assets from the assets folder
 */
public class AssetHandler implements AutoCloseable {
    /**
     * The map of asset subdirectories
     */
    private static final HashMap<String, Path> subdirectories = new HashMap<>();
    /**
     * The path to the assets folder
     */
    private static Path assetRoot;

    static {
        assetRoot = Path.of("assets");
    }

    /**
     * Sets the subdirectory for the assets
     */
    private Path assetFolder;

    /**
     * Creates a new AssetHandler for the specified asset type subdirectory
     *
     * @param assetFolder the subdirectory from where to load assets
     */
    private AssetHandler(Path assetFolder) {
        this.assetFolder = assetFolder;
    }

    /**
     * Sets the path to the assets folder
     *
     * @param path the path to the assets folder
     */
    public static void setAssetRoot(String path) throws IllegalStateException {
        if (subdirectories.isEmpty())
            assetRoot = Path.of(path);
        else
            throw new IllegalStateException("Cannot change asset folder after assets have been loaded");
    }

    /**
     * Returns or creates an asset handler for the specified subdirectory
     *
     * @param key the name of the subdirectory
     * @return the asset handler for the subdirectory
     */
    public static synchronized AssetHandler getInstance(String key) {
        Path assetFolder;
        if (subdirectories.containsKey(key)) {
            assetFolder = subdirectories.get(key);
        } else {
            assetFolder = assetRoot.resolve(key);
            try {
                if (!Files.exists(assetFolder)) {
                    Files.createDirectory(assetFolder);
                } else {
                    if (!Files.isDirectory(assetFolder)) {
                        throw new IllegalArgumentException("Asset type is not a directory");
                    }
                }
            } catch (IOException e) {
                throw new IllegalStateException("Error creating asset folder", e);
            }
            subdirectories.put(key, assetFolder);
        }
        return new AssetHandler(assetFolder);
    }

    /**
     * Gets the URL of the specified asset relative to the calling class
     * resource folder.
     * This means that it will try to look for the asset in the same folder
     * as the calling class file.
     * For assets compiled in the resources folder, the asset must be in the
     * same package as the calling class
     *
     * @param name the name of the asset
     * @return the URL of the asset, null if the caller class cannot be
     * found, or the asset cannot be found
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
     * Gets the input stream of the specified asset relative to the calling
     * class resource folder.
     * This means that it will try to look for the asset in the same folder
     * as the calling class file.
     * For assets compiled in the resources folder, the asset must be in the
     * same package as the calling class
     *
     * @param name the name of the asset
     * @return the input stream of the asset, null if the caller class
     * cannot be found, or the asset cannot be found
     */
    public static InputStream getResourceAsStream(String name) {
        try {
            Class<?> resourceCaller = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            return resourceCaller.getResourceAsStream(name);
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    /**
     * Gets the file from the assets folder
     *
     * @param name The name to the file
     * @return The file in the specified path
     */
    public Path getAsset(String name) {
        return assetRoot.resolve(name);
    }

    /**
     * Gets the file from the assets folder and opens it as a {@code FileInputStream}
     *
     * @param name The name to the file
     * @return An input stream of the file binary contents
     * @throws FileNotFoundException if the file does not exist
     */
    public InputStream getAssetAsStream(String name) throws IOException {
        return Files.newInputStream(assetFolder.resolve(name));
    }

    /**
     * Gets the URL of the specified asset from this asset handler's folder
     *
     * @param name the name of the asset
     * @return the URL of the asset
     * @throws IllegalStateException if the URL is malformed, this should never happen unless the file structure is
     *                               invalid
     */
    public URL getAssetURL(String name) throws IllegalStateException {
        try {
            return assetFolder.resolve(name).toUri().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException(
                    "Asset URL is malformed, there might have been an error or the file structure is invalid", e);
        }
    }

    /**
     * Gets all the files from the assets folder
     *
     * @return All the files from the specified path
     * @throws IllegalArgumentException if the path is not a folder
     */
    public Path[] getAssets() throws IllegalArgumentException, IOException {
        try (Stream<Path> files = Files.list(assetFolder)) {
            return files.toArray(Path[]::new);
        }
    }

    /**
     * Closes this resource, relinquishing any underlying resources. This method is invoked automatically on objects
     * managed by the {@code try}-with-resources statement.
     */
    @Override
    public void close() {
        assetFolder = null;
    }
}
