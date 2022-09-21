/**
 * A module that simplifies the use of external files in a java application. <br> It allows to create simple
 * configuration files and to read them in a simple way. <br> It also allows to read resources packaged in the jar file
 * in a simple way.
 */
module ierislib.properties {
	requires java.base;

	exports lib.ieris19.util.properties;
	exports lib.ieris19.util.assets;
}