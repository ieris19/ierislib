package lib.ieris19.util;

enum TypeChecker {
	INTERFACE,
	ENUM,
	RECORD,
	PRIMITIVE,
	ANNOTATION,
	ARRAY,
	CLASS;

	static String toString(Class<?> clazz) {
		String classType = clazz.isAnnotation() ? ANNOTATION.name() :
											 clazz.isEnum() ? ENUM.name() :
											 clazz.isRecord() ? RECORD.name() :
											 clazz.isInterface() ? INTERFACE.name() :
											 clazz.isArray() ? ARRAY.name() + " of" :
											 clazz.isPrimitive() ? PRIMITIVE.name() :
											 CLASS.name();
		return classType + " " + clazz.getName();
	}

	static boolean isType(Class<?> clazz, TypeChecker desiredType) {
		return TypeChecker.toString(clazz).startsWith(desiredType.name());
	}

	public static boolean notType(Class<?> clazz, TypeChecker desiredType) {
		return !TypeChecker.isType(clazz, desiredType);
	}
}
