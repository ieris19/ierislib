/**
 * The IerisLib User Interface module. <br> <br>
 *
 * This module contains the user interface framework for IerisLib. This framework is referred to as IerisFX and is a
 * JavaFX based framework. <br> <br>
 *
 * The framework is designed to be extended by the application desiring. By instantiating the
 * {@link lib.ieris19.util.ui.core.UIComponent UIComponent} users can create their own views. For this they need to
 * extend the {@link lib.ieris19.util.ui.mvvm.Model Model}, {@link lib.ieris19.util.ui.mvvm.ViewModel ViewModel} and
 * {@link lib.ieris19.util.ui.mvvm.ViewController ViewController} classes. The framework is designed to follow the MVVM
 * design pattern. <br> <br>
 *
 * A main method to initiate an app built with the framework could be implemented as follows:
 * <pre>
 *   <code>
 *   	public static void main(String[] args) {
 *    	UIComponent uiComponent = new UIComponent(...);
 *    	ViewMap.add("main", uiComponent);
 *    	//...
 *    	Application app = new Application();
 *    	app.setInit(Script)
 *    	app.setStart(Script)
 *    	app.setStop(Script)
 *    	app.launch();
 *   </code>
 *  </pre>
 */
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