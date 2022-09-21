package lib.ieris19.util.reflection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for reflection operations related to classes.
 */
public class ClassUtils {
	/**
	 * The string used as a package separator character in fully-qualified class names.
	 */
	private static final String PKG_SEPARATOR = ".";
	/**
	 * The string used as a directory separator character in paths.
	 */
	private static final String DIR_SEPARATOR = "/";
	/**
	 * The string used as a file extension for compiled sources, also known as classes to the runtime.
	 */
	private static final String CLASS_EXTENSION = ".class";

	/**
	 * Gets all the classes in the given package. The given package is loaded as a resource stream and file names are read
	 * and parsed in order to find corresponding classes. This method will also return underlying nested classes as
	 * separate elements of the set.
	 *
	 * @param pkg The package to search for classes in.
	 *
	 * @return A set of all classes in the given package.
	 *
	 * @throws ClassNotFoundException if the Package cannot be found by the System ClassLoader.
	 */
	public static Set<Class<?>> getClassesInPackage(Package pkg) throws ClassNotFoundException {
		String pkgPath = pkg.getName().replace(PKG_SEPARATOR, DIR_SEPARATOR);
		try (InputStream pkgInputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(pkgPath)) {
			if (pkgInputStream == null) {
				throw new ClassNotFoundException("Package " + pkg.getName() + " not found");
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(pkgInputStream));
			return reader.lines().map(line -> {
				if (line.endsWith(CLASS_EXTENSION)) {
					String fullyQualifiedClassName = pkg.getName() + PKG_SEPARATOR + line.substring(0, line.lastIndexOf('.'));
					try {
						return Class.forName(fullyQualifiedClassName);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				return null;
			}).collect(Collectors.toSet());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets all the classes in the given package. The given package is loaded as a resource stream and file names are read
	 * and parsed in order to find corresponding classes, they will only be added to the returned set if and only if they
	 * match the required type. This method will also return underlying nested classes as separate elements of the set.
	 *
	 * @param pkg         The package to search for classes in.
	 * @param desiredType The type that the classes must match.
	 *
	 * @return A set of all matching classes in the given package.
	 *
	 * @throws ClassNotFoundException if the Package cannot be found by the System ClassLoader.
	 * @see TypeChecker#isType(Class, TypeChecker) Type Checker comparator
	 */
	public static Set<Class<?>> getPackageClassesByType(Package pkg, TypeChecker desiredType)
	throws ClassNotFoundException {
		Set<Class<?>> tempClasses = getClassesInPackage(pkg);
		tempClasses.removeIf(clazz -> TypeChecker.notType(clazz, desiredType));
		return tempClasses;
	}

	//TODO: Move to MethodUtils class

	/**
	 * Attempts to invoke the method corresponding to the given name on the given class. The method must be public and
	 * static as it will be invoked on the class and not an object
	 *
	 * @param clazz      The class to invoke the method on.
	 * @param methodName The name of the method to invoke.
	 * @param args       The arguments to pass to the method.
	 *
	 * @return The return value of the method.
	 */
	public static Object executeStaticMethod(Class<?> clazz, String methodName, Object... args) {
		try {
			return clazz.getMethod(methodName).invoke(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Attempts to invoke the method corresponding to the given name on the given object. The method must be public and
	 * non-static as it will be invoked on the object and not the class.
	 *
	 * @param obj        The object to invoke the method on.
	 * @param methodName The name of the method to invoke.
	 * @param args       The arguments to pass to the method.
	 *
	 * @return The return value of the method.
	 */
	public static Object executeMethod(Object obj, String methodName, Object... args) {
		try {
			return obj.getClass().getMethod(methodName).invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//End of MethodUtils
}
