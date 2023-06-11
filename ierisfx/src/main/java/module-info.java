import com.ieris19.lib.ui.core.UIComponent;
import com.ieris19.lib.ui.mvvm.Model;
import com.ieris19.lib.ui.mvvm.ViewController;
import com.ieris19.lib.ui.mvvm.ViewModel;

/**
 * The IerisLib User Interface module. <br> <br>
 *
 * This module contains the user interface framework for IerisLib. This framework is referred to as IerisFX and is a
 * JavaFX based framework. <br> <br>
 *
 * The framework is designed to be extended by the application desiring. By instantiating the
 * {@link UIComponent UIComponent} users can create their own views. For this they need to
 * extend the {@link Model Model}, {@link ViewModel ViewModel} and
 * {@link ViewController ViewController} classes. The framework is designed to follow the MVVM
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
	requires static lombok;
	requires org.slf4j;

	requires javafx.base;
	requires javafx.graphics;
	requires javafx.fxml;

	requires ierislib.files.config;
	requires ierislib.files.assets;

	exports com.ieris19.lib.ui.core;
	exports com.ieris19.lib.ui.mvvm;
}