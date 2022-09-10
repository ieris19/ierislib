package lib.ieris19.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtils {
	private static final String PKG_SEPARATOR = ".";
	private static final String DIR_SEPARATOR = "/";
	private static final String CLASS_EXTENSION = ".class";

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

	public static Object instantiateClass(Class<?> clazz, Object... args) {
		try {
			Constructor<?> constructor = clazz.getConstructor();
			return constructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public static void executeMethod(Class<?> clazz, String methodName, Object... args) {
		try {
			clazz.getMethod(methodName).invoke(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
