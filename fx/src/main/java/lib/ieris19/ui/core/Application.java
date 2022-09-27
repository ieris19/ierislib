/*
 * Copyright 2021 Ieris19
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package lib.ieris19.ui.core;

import javafx.stage.Stage;
import lib.ieris19.commons.Script;

/**
 * The application class is the entry point of a JavaFX application. This class is a customizable concrete class
 * implementation of the {@link javafx.application.Application Application} class. This class is responsible for
 * initializing the IerisFX application.
 */
public class Application extends javafx.application.Application {

	/**
	 * The initializer method of the application. This script is responsible for initializing the application.
	 */
	private Script initScript;
	/**
	 * The main method of the application. This script is responsible for starting the application.
	 */
	private Script startScript;
	/**
	 * The stop method of the application. This script is responsible for cleanup after the application has terminated.
	 */
	private Script stopScript;

	/**
	 * Creates a new Application instance with empty scripts. The scripts can be set later. This limits the application to
	 * only the default behaviour unless the scripts are set afterwards.
	 */
	public Application() {
		Script empty = (args) -> {};
		setInit(empty);
		setStart(empty);
		setStop(empty);
	}

	/**
	 * Sets the initialization script for the application
	 *
	 * @param initScript the initialization script
	 */
	public void setInit(Script initScript) {
		this.initScript = initScript;
	}

	/**
	 * Sets the start script for the application
	 *
	 * @param startScript
	 */
	public void setStart(Script startScript) {
		this.startScript = startScript;
	}

	/**
	 * Sets the stop script for the application
	 *
	 * @param stopScript
	 */
	public void setStop(Script stopScript) {
		this.stopScript = stopScript;
	}

	/**
	 * The application initialization method. This method is called immediately after the Application class is loaded and
	 * constructed. An application may provide an implementation of this method to perform any necessary initialization
	 * through the {@link #setInit(Script) setInit} method.
	 *
	 * <p>
	 * The implementation of this method provided by the Application class does nothing.
	 * </p>
	 *
	 * <p>
	 * NOTE: This method is not called on the IerisFX Main Thread
	 * </p>
	 *
	 * @throws Exception if something goes wrong
	 */
	@Override public void init() throws Exception {
		Thread.currentThread().setName("IerisFX Initializer");
		initScript.execute();
	}

	/**
	 * The main entry point for all IerisFX applications. The start method is called after the init method has returned,
	 * and after the system is ready for the application to begin running. An application may provide an implementation of
	 * this method to start any necessary secondary processes through the {@link #setStart(Script) setStart} method.
	 *
	 * <p>
	 * NOTE: This method is called on the IerisFX Main Thread
	 * </p>
	 *
	 * @param primaryStage the primary stage for this application, onto which the application scene can be set.
	 *                     Applications may create other stages, if needed, but they will not be primary stages.
	 *
	 * @throws Exception if something goes wrong
	 */
	@Override public void start(Stage primaryStage) throws Exception {
		startScript.execute();
		ViewManager vm = new ViewManager();
		vm.start(primaryStage);
	}

	/**
	 * This method is called when the application should stop, and provides a convenient place to prepare for application
	 * exit and destroy resources. An application may provide an implementation of this method to perform any necessary
	 * cleanup through the {@link #setStop(Script) setStop} method.
	 *
	 * <p>
	 * NOTE: This method is called on the IerisFX Main Thread.
	 * </p>
	 *
	 * @throws Exception if something goes wrong
	 */
	@Override public void stop() throws Exception {
		stopScript.execute();
	}
}
