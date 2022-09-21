package lib.ieris19.util.ui.mvvm;

import javafx.scene.layout.Region;
import lib.ieris19.util.ui.core.UIComponent;
import lib.ieris19.util.ui.core.ViewManager;

/**
 * This class represents the view controller of a UIComponent. This class is responsible for linking the user and
 * system. It deals with user interaction, it is responsible for updating the view, and being observed by the
 * {@link ViewModel}.
 */
public abstract class ViewController {
	/**
	 * The root node of the view. All visual elements of the view are children of this node. This node is typically
	 * loaded from the fxml file.
	 */
	protected Region root;

	/**
	 * Returns the root node of the view.
	 * @return the root node of the view
	 */
	public Region getRoot() {
		return root;
	}

	/**
	 * Sets the root node of the view.
	 * @param root the root node of the view
	 */
	public abstract void init(ViewManager viewManager, UIComponent view, Region root);

	/**
	 * Returns the view to the initial state, regardless of the current state.
	 */
	public abstract void reset();
}
