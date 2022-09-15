package lib.ieris19.util;

import lib.ieris19.util.testPackage.RegularClass;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ClassUtilsTest {
	Package testPackage = RegularClass.class.getPackage();
	@Test void ClassesInPackageTest() {
		System.out.println("Start Test");
		try {
			Set<Class<?>> packageClasses = ClassUtils.getClassesInPackage(testPackage);
			packageClasses.forEach((clazz) -> {System.out.println(TypeChecker.toString(clazz));});
			assert packageClasses.size() == 6;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			assert false;
		}
	}

	@Test void ClassTypes() {
		System.out.println("Start Test");
		try {
			Set<Class<?>> packageClassesOfType = ClassUtils.getPackageClassesByType(testPackage, TypeChecker.INTERFACE);
			packageClassesOfType.forEach((clazz) -> {System.out.println(TypeChecker.toString(clazz));});
			assert packageClassesOfType.size() == 1;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			assert false;
		}
	}
}