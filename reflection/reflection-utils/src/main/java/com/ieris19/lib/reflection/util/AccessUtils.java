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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * A class containing utility methods for manipulating access modifiers using reflection.
 */
public class AccessUtils {
    /**
     * Returns a constructor that is accessible and can be invoked immediately.
     *
     * @param clazz          The class to get the constructor from.
     * @param parameterTypes The parameter types taken in as arguments by the desired constructor.
     * @return The accessible constructor.
     * @throws ReflectiveOperationException If the constructor could not be found or made accessible.
     * @see Class#getConstructor(Class...) Class.getConstructor()
     */
    public static Constructor<?> getAccessibleConstructor(Class<?> clazz, Class<?>... parameterTypes)
            throws ReflectiveOperationException {
        Constructor<?> target = clazz.getConstructor(parameterTypes);
        target.setAccessible(true);
        return target;
    }

    /**
     * Returns a method that is accessible and can be invoked immediately.
     *
     * @param clazz          The class to get the method from.
     * @param methodName     The name of the desired method.
     * @param parameterTypes The parameter types taken in as arguments by the desired method.
     * @return The accessible method.
     * @throws ReflectiveOperationException If the method could not be found or made accessible.
     * @see Class#getMethod(String, Class...) Class.getMethod()
     */
    public static Method getAccessibleMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes)
            throws ReflectiveOperationException {
        Method target = clazz.getMethod(methodName, parameterTypes);
        target.setAccessible(true);
        return target;
    }

    /**
     * Returns a field that is accessible and can be manipulated immediately.
     *
     * @param clazz     The class to get the field from.
     * @param fieldName The name of the desired field.
     * @return The accessible field.
     * @throws ReflectiveOperationException If the field could not be found or made accessible.
     * @see Class#getField(String) Class.getField()
     */
    public static Field getAccessibleField(Class<?> clazz, String fieldName) throws ReflectiveOperationException {
        Field target = clazz.getField(fieldName);
        target.setAccessible(true);
        return target;
    }

    /**
     * Sets a constructor to be permanently accessible.
     * <p>
     * This method uses internal methods to set the constructor to be permanently accessible.
     * This method is not guaranteed to work on all JVMs. If it does not work, it will throw an
     * {@link ReflectiveOperationException}. If it does work, the method will be permanently accessible.
     * </p>
     * <p>
     * Note that the implications of this method mean changing the accessibility of a method completely.
     * This means that the method will be accessible regardless of the modifiers set on the method.
     * This method should be used with EXTREME CAUTION.
     * </p>
     *
     * @param clazz          The class to get the method from.
     * @param parameterTypes The parameter types taken in as arguments by the method.
     * @throws ReflectiveOperationException If the method could not be found or made accessible.
     * @see Class#getConstructor(Class[]) Class.getMethod()
     * @see java.lang.reflect.AccessibleObject AccessibleObject
     */
    public static void setPermanentAccessibleConstructor(Class<?> clazz, Class<?>... parameterTypes)
            throws ReflectiveOperationException {
        Method constructorAccessor;
        try {
            constructorAccessor = Class.class.getDeclaredMethod("getConstructor0", Class[].class, int.class);
            constructorAccessor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ReflectiveOperationException
                    ("Could not find internal accessor method, your JVM runtime is not supported.");
        }
        Method target = (Method) constructorAccessor.invoke(clazz, parameterTypes, 1);
        target.setAccessible(true);
    }

    /**
     * Sets a method to be permanently accessible.
     * <p>
     * This method uses internal methods to set the method to be permanently accessible.
     * This method is not guaranteed to work on all JVMs. If it does not work, it will throw an
     * {@link ReflectiveOperationException}. If it does work, the method will be permanently accessible.
     * </p>
     * <p>
     * Note that the implications of this method mean changing the accessibility of a method completely.
     * This means that the method will be accessible regardless of the modifiers set on the method.
     * This method should be used with EXTREME CAUTION.
     * </p>
     *
     * @param clazz          The class to get the method from.
     * @param methodName     The name of the desired method.
     * @param parameterTypes The parameter types taken in as arguments by the method.
     * @throws ReflectiveOperationException If the method could not be found or made accessible.
     * @see Class#getMethod(String, Class...) Class.getMethod()
     * @see java.lang.reflect.AccessibleObject AccessibleObject
     */
    public static void setPermanentAccessibleMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes)
            throws ReflectiveOperationException {
        Method methodAccessor;
        try {
            methodAccessor = Class.class.getDeclaredMethod("getMethod0", String.class, Class[].class);
            methodAccessor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ReflectiveOperationException
                    ("Could not find internal accessor method, your JVM runtime is not supported.");
        }
        Method target = (Method) methodAccessor.invoke(clazz, new Object[]{methodName, parameterTypes});
        target.setAccessible(true);
    }

    /**
     * Sets a field to be permanently accessible using internal methods.
     * <p></p>
     * This method is not guaranteed to work on all JVMs. If it does not work, it will throw an
     * {@link ReflectiveOperationException}. If it does work, the method will be permanently accessible.
     * </p>
     * <p>
     * Note that the implications of this method mean changing the accessibility of a field completely.
     * This means that the method will be accessible regardless of the modifiers set on the method.
     * This method should be used with EXTREME CAUTION.
     * </p>
     *
     * @param clazz     The class to get the method from.
     * @param fieldName The name of the desired field.
     * @throws ReflectiveOperationException If the method could not be found or made accessible.
     * @see Class#getField(String) Class.getField()
     * @see java.lang.reflect.AccessibleObject AccessibleObject
     */
    public static void setPermanentAccessibleField(Class<?> clazz, String fieldName)
            throws ReflectiveOperationException {
        Method fieldAccessor;
        try {
            fieldAccessor = Class.class.getDeclaredMethod("privateGetDeclaredFields", boolean.class);
            fieldAccessor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ReflectiveOperationException
                    ("Could not find internal accessor method, your JVM runtime is not supported.");
        }
        Field[] fields = (Field[]) fieldAccessor.invoke(clazz, false);
        for (Field field : fields) {
            if (Objects.equals(field.getName(), fieldName)) {
                field.setAccessible(true);
                return;
            }
        }
    }
}
