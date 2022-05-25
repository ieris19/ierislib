package ui.login;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.Model;

public class LogInApplication extends Application {
	Model control;

	@Override public void start(Stage stage) {
		control = new ModelController();
		ViewHandler view = new ViewHandler(control);
		view.start(stage);
	}

	@Override public void stop() {
	}

	@Override public void init() {
	}
}
