module ierislib.ui {
	requires java.base;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires javafx.web;

	requires ierislib.commons;
	requires ierislib.log;
	requires ierislib.properties;

	exports lib.ieris19.util.ui.core;
	exports lib.ieris19.util.ui.mvvm;
}