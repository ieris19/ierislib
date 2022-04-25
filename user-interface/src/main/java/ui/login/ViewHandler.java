package ui.login;

import javafx.scene.layout.Region;
import ui.Model;
import ui.ViewManager;

public class ViewHandler extends ViewManager {
	public ViewHandler(Model control) {
		super(control);
		controllers.add(new LogInController()); //0
	}

	@Override public void openView(int viewId) {
		Region root = switch (viewId) {
			case 0 -> loadView("log-in.fxml", controllers.get(0));
			default -> throw new IllegalStateException("Unexpected value: " + viewId);
		};
		showScene(root);
	}
}
