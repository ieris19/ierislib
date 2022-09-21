package lib.ieris19.util.ui.core;

import lib.ieris19.util.assets.AssetHandler;
import lib.ieris19.util.ui.mvvm.Model;
import lib.ieris19.util.ui.mvvm.ViewController;
import lib.ieris19.util.ui.mvvm.ViewModel;

import java.net.URL;

/**
 * This class represents a user-interface component that is completely standalone, containing all that is needed to run
 * this specific View. This makes UIComponents encapsulated, reusable and easy to manage.
 */
public class UIComponent {
	/**
	 * The file path to the FXML file of the UIComponent
	 */
	private final URL fxmlFile;
	/**
	 * The business logic of the UIComponent. This class is typically either a remote object in a different process or a
	 * local object in the same process that is forwarded methods from the {@link ViewController} through the
	 * {@link ViewModel}.
	 */
	private final Model model;
	/**
	 * The view model of the UIComponent. This class is responsible for linking {@link Model} and
	 * {@link ViewController view} observing the view and forwarding events to the model.
	 */
	private final ViewModel viewModel;
	/**
	 * The view controller of the UIComponent. This class is responsible for linking the user and
	 * system. It deals with user interaction, it is responsible for updating the view, and being observed by the
	 * {@link ViewModel}.
	 */
	private final ViewController controller;

	/**
	 * Creates a new UIComponent instance with the given parameters
	 * @param fxmlName the name of the FXML file
	 * @param model the model of the UIComponent
	 * @param viewModel the view model of the UIComponent
	 * @param controller the view controller of the UIComponent
	 */
	UIComponent(String fxmlName, Model model, ViewModel viewModel, ViewController controller) {
		this.model = model;
		this.viewModel = viewModel;
		this.viewModel.setModel(model);
		this.controller = controller;
		URL fxml = AssetHandler.getResource(fxmlName);
		if (fxml != null) {
			this.fxmlFile = fxml;
		} else {
			throw new IllegalArgumentException("Cannot find fxml file");
		}
	}

	/**
	 * Returns the FXML file of the UIComponent
	 * @return the FXML file of the UIComponent
	 */
	public URL getFXML() {
		return fxmlFile;
	}

	/**
	 * Returns the model of the UIComponent
	 * @return the model of the UIComponent
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Returns the view model of the UIComponent
	 * @return the view model of the UIComponent
	 */
	public ViewModel getViewModel() {
		return viewModel;
	}

	/**
	 * Returns the view controller of the UIComponent
	 * @return the view controller of the UIComponent
	 */
	public ViewController getController() {
		return controller;
	}
}
