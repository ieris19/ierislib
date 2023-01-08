/**
 * A module that allows to log messages in a file and in the console. <br> It also allows to create a custom logger that
 * can be defined through a simple builder.
 */
module ierislib.util.log {

	requires ierislib.common;
	requires ierislib.log.core;

	exports com.ieris19.lib.util.log.custom;
}