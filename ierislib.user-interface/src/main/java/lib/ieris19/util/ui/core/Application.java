package lib.ieris19.util.ui.core;

import javafx.stage.Stage;
import lib.ieris19.util.Script;

public class Application extends javafx.application.Application {

	private Script initScript;
	private Script startScript;
	private Script stopScript;

	public void setInit(Script initScript) {
		this.initScript = initScript;
	}

	public void setStart(Script startScript) {
		this.startScript = startScript;
	}

	public void setStop(Script stopScript) {
		this.stopScript = stopScript;
	}

	/**
	 * The application initialization method. This method is called immediately after the Application class is loaded and
	 * constructed. An application may override this method to perform initialization prior to the actual starting of the
	 * application.
	 *
	 * <p>
	 * The implementation of this method provided by the Application class does nothing.
	 * </p>
	 *
	 * <p>
	 * NOTE: This method is not called on the JavaFX Application Thread. An application must not construct a Scene or a
	 * Stage in this method. An application may construct other JavaFX objects in this method.
	 * </p>
	 *
	 * @throws Exception if something goes wrong
	 */
	@Override public void init() throws Exception {
		initScript.execute(new String[0]);
	}

	/**
	 * The main entry point for all IerisFX applications. The start method is called after the init method has returned,
	 * and after the system is ready for the application to begin running.
	 *
	 * <p>
	 * NOTE: This method is called on the JavaFX Application Thread.
	 * </p>
	 *
	 * @param primaryStage the primary stage for this application, onto which the application scene can be set.
	 *                     Applications may create other stages, if needed, but they will not be primary stages.
	 *
	 * @throws Exception if something goes wrong
	 */
	@Override public void start(Stage primaryStage) throws Exception {
		startScript.execute(new String[0]);
		ViewManager vm = new ViewManager();
		vm.start(primaryStage);
	}

	/**
	 * This method is called when the application should stop, and provides a convenient place to prepare for application
	 * exit and destroy resources.
	 *
	 * <p>
	 * The implementation of this method provided by the Application class does nothing.
	 * </p>
	 *
	 * <p>
	 * NOTE: This method is called on the JavaFX Application Thread.
	 * </p>
	 *
	 * @throws Exception if something goes wrong
	 */
	@Override public void stop() throws Exception {
		stopScript.execute(new String[0]);
	}
}
