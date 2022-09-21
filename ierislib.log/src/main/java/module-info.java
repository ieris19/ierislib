/**
 * A module that allows to log messages in a file and in the console. <br>
 * It also allows to create a custom logger that can be defined through a simple builder.
 */
module ierislib.log {
	requires java.base;
	requires ierislib.commons;

	exports lib.ieris19.util.log;
	exports lib.ieris19.util.log.ieris;
	exports lib.ieris19.util.log.custom;
}