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

package com.ieris19.lib.context.reflection;

import com.ieris19.lib.reflection.error.NoSuchPackageException;
import com.ieris19.lib.reflection.util.ClassUtils;
import com.ieris19.lib.reflection.util.PackageUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A class that allows annotation scanning of packages.
 * <p>
 * This class creates an array of packages when initialized and then allows the user to scan
 * retrieve classes that are annotated with a specific annotation.
 * <br>
 * The packages that are scanned are all sub-packages of the base package that is given
 * when the class is initialized.
 * <br>
 * The class uses the {@link PackageUtils} class to discover packages.
 */
public class AnnotationScanner {
    /**
     * An array of all packages that should be scanned.
     */
    private final Package[] packageSet;

    /**
     * Creates a new {@link AnnotationScanner} that scans all sub-packages of the given base
     *
     * @param basePackage The base package to scan. Use an empty string to scan all packages visible
     *                    to the class loader.
     */
    public AnnotationScanner(String basePackage) {
        this.packageSet = PackageUtils.discoverPackages(basePackage);
    }

    /**
     * Finds all classes that are declared with the given annotation.
     *
     * @param annotation desired annotation to find
     * @return An array of all classes that are declared with the given annotation.
     */
    public Class<?>[] getAnnotatedClasses(Class<? extends Annotation> annotation) {
        if (!includesTargets(annotation, ElementType.TYPE)) {
            throw new IllegalArgumentException("Annotation does not target classes");
        }
        return getClasses()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .toArray(Class<?>[]::new);
    }

    /**
     * Finds all fields that are declared with the given annotation.
     *
     * @param annotation desired annotation to find
     * @return An array of all fields that are declared with the given annotation.
     */
    public Field[] getAnnotatedFields(Class<? extends Annotation> annotation) {
        if (!includesTargets(annotation, ElementType.FIELD)) {
            throw new IllegalArgumentException("Annotation does not target fields");
        }
        return getClasses()
                .flatMap(clazz -> Arrays.stream(clazz.getDeclaredFields()))
                .filter(field -> field.isAnnotationPresent(annotation))
                .toArray(Field[]::new);
    }

    /**
     * Finds all methods that are declared with the given annotation.
     *
     * @param annotation desired annotation to find
     * @return An array of all methods that are declared with the given annotation.
     */
    public Method[] getAnnotatedMethods(Class<? extends Annotation> annotation) {
        if (!includesTargets(annotation, ElementType.METHOD)) {
            throw new IllegalArgumentException("Annotation does not target methods");
        }
        return getClasses()
                .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
                .filter(method -> method.isAnnotationPresent(annotation))
                .toArray(Method[]::new);
    }

    private static boolean includesTargets(Class<? extends Annotation> at, ElementType type) {
        ElementType[] targets = at.getAnnotation(Target.class).value();
        for (ElementType target : targets) {
            if (target == type) {
                return true;
            }
        }
        return false;
    }

    private Stream<Class<?>> getClasses() {
        return Arrays.stream(packageSet).flatMap(pkg -> {
            try {
                return ClassUtils.getClassesInPackage(pkg).stream();
            } catch (NoSuchPackageException e) {
                throw new IllegalStateException("Previously discovered package does not exist anymore", e);
            }
        });
    }
}
