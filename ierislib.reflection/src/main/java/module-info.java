/**
 * A module that provides a simple API to use reflective programming in a simplified way. <br> Complex reflection
 * operations can be done manually but this module allows certain common operations to be shortened.
 */
module ierislib.reflection {
	requires java.base;
	requires java.compiler;

	exports lib.ieris19.util.reflection;
}