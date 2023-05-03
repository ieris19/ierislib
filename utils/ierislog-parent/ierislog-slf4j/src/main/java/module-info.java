/**
 * This module provides a bridge between Ierislog and the slf4j facade. This allows the use of Ierislog in projects that
 * use slf4j.
 */
module ierislib.log.slf4j {
	requires org.slf4j;
	requires ierislib.log.core;

	provides org.slf4j.spi.SLF4JServiceProvider with com.ieris19.lib.util.log.slf4j.SLF4JServiceProvider;

	exports com.ieris19.lib.util.log.slf4j;
}