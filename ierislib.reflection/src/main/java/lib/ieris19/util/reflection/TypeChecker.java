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

package lib.ieris19.util.reflection;

/**
 * Utility class for reflection operations related to types. This class is used to check whether a class is an instance
 * of a primitive type, array, or object (enum, interface, record or class).
 */
public enum TypeChecker {
	/**
	 * Abstract instructions for methods that need to be implemented
	 */
	INTERFACE,
	/**
	 * A class that creates all instances of the given within itself
	 */
	ENUM,
	/**
	 * A class that holds data and has no methods
	 */
	RECORD,
	/**
	 * A value associated to a keyword within the Java language
	 */
	PRIMITIVE,
	/**
	 * A value that decorates a class, method field or parameter with additional information for the compiler or the
	 * runtime
	 */
	ANNOTATION,
	/**
	 * A collection of Objects or primitives that can be accessed by index
	 */
	ARRAY,
	/**
	 * A class that can hold fields and have methods. This is the base type for all other types within the Java language.
	 * All other types are syntactic sugar for more narrow use cases.
	 */
	CLASS;

	/**
	 * Checks whether the given class is an instance of one of the Enum constants.
	 *
	 * @param clazz The class to check.
	 *
	 * @return The Enum constant that the given class is an instance of plus the fully classified name of the class.
	 */
	static String toString(Class<?> clazz) {
		String classType = clazz.isAnnotation() ?
											 ANNOTATION.name() :
											 clazz.isEnum() ?
											 ENUM.name() :
											 clazz.isRecord() ?
											 RECORD.name() :
											 clazz.isInterface() ?
											 INTERFACE.name() :
											 clazz.isArray() ? ARRAY.name() + " of" : clazz.isPrimitive() ? PRIMITIVE.name() : CLASS.name();
		return classType + " " + clazz.getName();
	}

	/**
	 * Checks whether the given class is of the desired type.
	 *
	 * @param clazz       The class to check.
	 * @param desiredType The desired type.
	 *
	 * @return Whether the given class is of the desired type.
	 */
	static boolean isType(Class<?> clazz, TypeChecker desiredType) {
		return TypeChecker.toString(clazz).startsWith(desiredType.name());
	}

	/**
	 * Checks whether the given class is not an instance of the desired type.
	 *
	 * @param clazz The class to check.
	 *
	 * @return Whether the given class is not an instance of the desired type.
	 */
	public static boolean notType(Class<?> clazz, TypeChecker desiredType) {
		return !TypeChecker.isType(clazz, desiredType);
	}
}
