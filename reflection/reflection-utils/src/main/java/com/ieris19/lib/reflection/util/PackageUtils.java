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

package com.ieris19.lib.reflection.util;

import java.util.Arrays;

/**
 * A class containing utility methods for accessing packages using reflection.
 */
public class PackageUtils {
    /**
     * Discovers all packages that are sub-packages of the given base package.
     * <p>
     * This method uses the {@link ClassLoader#getDefinedPackages()} method to discover all
     * packages that exist and then filters them based on the given base package. This means
     * that if the package names start with the same string as the base package, they will be
     * included in the result.
     * </p>
     *
     * @param basePackage The base package to discover sub-packages of.
     * @return An array of all packages that are sub-packages of the given base package.
     */
    public static Package[] discoverPackages(String basePackage) {
        ClassLoader loader = ReflectionUtils.getClassLoader();
        return Arrays.stream(loader.getDefinedPackages())
                     .filter(p -> p.getName().startsWith(basePackage))
                     .toArray(Package[]::new);
    }

    /**
     * Discovers all packages that are sub-packages of the given base package.
     *
     * @param basePackage The base package to discover sub-packages of.
     * @return An array of all packages that are sub-packages of the given base package.
     */
    public static Package[] discoverPackages(Package basePackage) {
        return discoverPackages(basePackage.getName());
    }

    /**
     * Returns the package that the given class belongs to.
     *
     * @param clazz The class to get the package of.
     * @return The package that the given class belongs to.
     */
    public static Package declaringPackage(Class<?> clazz) {
        return clazz.getPackage();
    }
}
