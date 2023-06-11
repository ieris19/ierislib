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

import com.ieris19.lib.reflection.error.NoSuchPackageException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for reflection operations related to classes.
 * <p>
 * The class provides methods that abstract away the complexity of some
 * common reflection operations related to classes.
 * </p>
 */
public class ClassUtils {
    /**
     * Private constructor to prevent instantiation
     */
    private ClassUtils() {
    }

    /**
     * Gets all the classes in the given package. The given package is loaded as a resource stream and file names are read
     * and parsed in order to find corresponding classes. This method will also return underlying nested classes as
     * separate elements of the set.
     *
     * @param pkg The package to search for classes in.
     * @return A set of all classes in the given package.
     * @throws NoSuchPackageException if the Package cannot be found or loaded
     */
    public static Set<Class<?>> getClassesInPackage(Package pkg) throws NoSuchPackageException {
        String pkgPath = pkg.getName().replace('.', '/');
        try (InputStream pkgInputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(pkgPath)) {
            if (pkgInputStream == null) {
                throw new NoSuchPackageException("Package " + pkg.getName() + " not found");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(pkgInputStream));
            Set<Class<?>> classSet = reader.lines().map(line -> {
                if (line.endsWith(".class")) {
                    String fullyQualifiedClassName = pkg.getName() + '.' + line.substring(0, line.lastIndexOf('.'));
                    try {
                        return Class.forName(fullyQualifiedClassName);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }).collect(Collectors.toSet());
            classSet.removeIf(Objects::isNull);
            return classSet;
        } catch (IOException e) {
            throw new NoSuchPackageException("Could not load the package", e);
        }
    }

    /**
     * Gets all the classes in the given package. The given package is loaded as a resource stream and file names are read
     * and parsed in order to find corresponding classes, they will only be added to the returned set if and only if they
     * match the required type. This method will also return underlying nested classes as separate elements of the set.
     *
     * @param pkg         The package to search for classes in.
     * @param desiredType The type that the classes must match.
     * @return A set of all matching classes in the given package.
     * @throws NoSuchPackageException if the Package cannot be found by the System ClassLoader.
     * @see TypeChecker#isType(Class, TypeChecker) Type Checker comparator
     */
    public static Set<Class<?>> getPackageClassesByType(Package pkg, TypeChecker desiredType)
            throws NoSuchPackageException {
        Set<Class<?>> tempClasses = getClassesInPackage(pkg);
        tempClasses.removeIf(clazz -> TypeChecker.notType(clazz, desiredType));
        return tempClasses;
    }
}
