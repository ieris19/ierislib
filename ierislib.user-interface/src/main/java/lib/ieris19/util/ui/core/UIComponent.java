package lib.ieris19.util.ui.core;

import lib.ieris19.util.ui.mvvm.Model;
import lib.ieris19.util.ui.mvvm.ViewController;
import lib.ieris19.util.ui.mvvm.ViewModel;

import java.net.URL;

/**
 * This class represents a user-interface component that is completely standalone, containing all that is needed to run
 * this specific View
 */
public class UIComponent {
	private final URL fxmlFile;
	private final Model model;
	private final ViewModel viewModel;
	private final ViewController controller;

	UIComponent(String fxmlName, Model model, ViewModel viewModel, ViewController controller) {
		this.model = model;
		this.viewModel = viewModel;
		this.viewModel.setModel(model);
		this.controller = controller;
		URL fxml = controller.getClass().getResource(fxmlName);
		if (fxml != null) {
			this.fxmlFile = fxml;
		} else {
			throw new IllegalArgumentException("Cannot find fxml file");
		}
	}

	public URL getFXML() {
		return fxmlFile;
	}

	public Model getModel() {
		return model;
	}

	public ViewModel getViewModel() {
		return viewModel;
	}

	public ViewController getController() {
		return controller;
	}
}
