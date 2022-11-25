/**
 * This module provides a bridge between Ierislog and the slf4j facade. This allows the use of Ierislog in projects that
 * use slf4j.
 */
module ierislib.util.slf4j {
	requires java.base;
	requires org.slf4j;
	requires ierislib.util.log;

	exports com.ieris19.lib.util.log.slf4j;
}