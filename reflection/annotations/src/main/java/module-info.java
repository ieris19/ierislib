/**
 * Annotation processing module for Ierislib. <br> This module contains all the annotations used in Ierislib and the
 * annotation processor that generates the code for the annotations. <br> This module is intended to be as customizable
 * and flexible as possible, so it can be used in any project.
 */
module ierislib.reflection.annotations {
	requires java.base;
	requires java.compiler;

	exports com.ieris19.lib.reflection.annotations.processor;
}