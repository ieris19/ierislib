package lib.ieris19.util;

import lib.ieris19.util.log.IerisLog;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ClassUtilsTest {
	@Test void ClassesInPackageTest() {
		System.out.println("Start Test");
		try {
			Set<Class<?>> packageClasses = ClassUtils.getClassesInPackage(IerisLog.class.getPackage());
			packageClasses.forEach(System.out::println);
			assert true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			assert false;
		}
	}
}