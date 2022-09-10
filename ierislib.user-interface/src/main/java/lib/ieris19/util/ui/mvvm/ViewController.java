package lib.ieris19.util.ui.mvvm;

import javafx.scene.layout.Region;
import lib.ieris19.util.ui.core.UIComponent;
import lib.ieris19.util.ui.core.ViewManager;

public abstract class ViewController {
	protected Region root;

	public Region getRoot() {
		return root;
	}

	public void init(ViewManager viewManager, UIComponent view, Region root) {
	}

	public void reset() {
	}
}
