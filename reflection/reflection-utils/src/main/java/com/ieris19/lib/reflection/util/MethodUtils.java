package com.ieris19.lib.reflection.util;

/**
 * A class containing utility methods for invoking methods using reflection.
 * <p>
 * The class provides methods that abstract away the complexity of some
 * common reflection operations related to classes.
 * </p>
 */
public class MethodUtils {
    /**
     * Attempts to invoke the method corresponding to the given name on the given class. The method must be public and
     * static as it will be invoked on the class and not an object
     *
     * @param clazz      The class to invoke the method on.
     * @param methodName The name of the method to invoke.
     * @param args       The arguments to pass to the method.
     * @return The return value of the method.
     * @throws ReflectiveOperationException If there is an error invoking the method, this exception
     *                                      is a parent to the exceptions that can actually be thrown.
     */
    public static Object executeStaticMethod(Class<?> clazz, String methodName, Object... args)
            throws ReflectiveOperationException {
        return clazz.getMethod(methodName).invoke(args);
    }

    /**
     * Attempts to invoke the method corresponding to the given name on the given object. The method must be public and
     * non-static as it will be invoked on the object and not the class.
     *
     * @param obj        The object to invoke the method on.
     * @param methodName The name of the method to invoke.
     * @param args       The arguments to pass to the method.
     * @return The return value of the method.
     * @throws ReflectiveOperationException If there is an error invoking the method, this exception
     *                                      is a parent to the exceptions that can actually be thrown.
     */
    public static Object executeMethod(Object obj, String methodName, Object... args)
            throws ReflectiveOperationException {
        return obj.getClass().getMethod(methodName).invoke(obj, args);
    }
}
