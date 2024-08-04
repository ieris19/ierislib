/**
 * A module that simplifies the use of property files for configuration in a java application. <br> It allows to create
 * simple configuration files and to read them in a simple way.
 */
module ierislib.files.config {
    requires java.base;
    requires ini4j;

    exports com.ieris19.lib.files.config.error;
    exports com.ieris19.lib.files.config.api;
}