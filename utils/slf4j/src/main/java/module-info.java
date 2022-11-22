module ierislib.util.slf4j {
	requires java.base;
	requires org.slf4j;
	requires ierislib.util.log;

	exports com.ieris19.lib.util.log.slf4j;
}