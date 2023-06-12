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

/**
 * A class containing utility methods for general reflection tasks that don't fit in any other
 * class in this module.
 */
public class ReflectionUtils {
    /**
     * This method will try to get the ClassLoader from the given class.
     * If it fails, it will try to get a different ClassLoader as a fallback.
     *
     * @param clazz The class to get the ClassLoader from.
     * @return A ClassLoader for the given class or one of its fallbacks.
     * <ol>
     *      <li>Parameter Class ClassLoader</li>
     *      <li>Thread Context ClassLoader</li>
     *      <li>ClassUtils Own ClassLoader</li>
     *      <li>System ClassLoader</li>
     *      <li>Null if all else fails</li>
     * </ol>
     */
    public static ClassLoader getClassLoader(Class<?> clazz) {
        try {
            return clazz.getClassLoader();
        } catch (SecurityException e) {
            return getClassLoader();
        }
    }

    /**
     * This method will try to get the ClassLoader from the current thread.
     * If it fails, it will try to get a different ClassLoader as a fallback.
     *
     * @return A ClassLoader for the given class or one of its fallbacks.
     * <ol>
     *      <li>Thread Context ClassLoader</li>
     *      <li>ClassUtils Own ClassLoader</li>
     *      <li>System ClassLoader</li>
     *      <li>Null if all else fails</li>
     * </ol>
     */
    public static ClassLoader getClassLoader() {
        ClassLoader loader = null;
        for (ClassLoaderTypes type : ClassLoaderTypes.values()) {
            loader = type.getClassLoader();
            if (loader != null) {break;}
        }
        return loader;
    }

    /**
     * A small enum to help define the different types of acceptable ClassLoaders and how to get them.
     */
    private enum ClassLoaderTypes {
        CONTEXT,
        SELF,
        SYSTEM;

        public ClassLoader getClassLoader() {
            try {
                return switch (this) {
                    case CONTEXT -> Thread.currentThread().getContextClassLoader();
                    case SELF -> ClassUtils.class.getClassLoader();
                    case SYSTEM -> ClassLoader.getSystemClassLoader();
                };
            } catch (SecurityException e) {
                return null;
            }
        }
    }
}
