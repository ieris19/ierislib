/**
 * The IerisLib User Interface module.
 * <p>
 * This module contains the user interface framework for IerisLib. This framework is referred to as
 * IerisFX and is a JavaFX based framework.
 * </p>
 */
module ierislib.ui {
    requires static lombok;
    requires org.slf4j;

    requires javafx.base;
    requires javafx.graphics;
    requires javafx.fxml;

    requires ierislib.files.config;
    requires ierislib.files.assets;

    exports com.ieris19.lib.ui.core;
    exports com.ieris19.lib.ui.core.components;
    exports com.ieris19.lib.ui.core.util;
    exports com.ieris19.lib.ui.core.components.impl;
}