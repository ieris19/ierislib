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

package com.ieris19.lib.ui.core;

import com.ieris19.lib.files.assets.AssetHandler;
import com.ieris19.lib.ui.mvvm.ViewController;
import com.ieris19.lib.ui.mvvm.Model;
import com.ieris19.lib.ui.mvvm.ViewModel;

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
	 * The view controller of the UIComponent. This class is responsible for linking the user and system. It deals with
	 * user interaction, it is responsible for updating the view, and being observed by the {@link ViewModel}.
	 */
	private final ViewController controller;

	/**
	 * Creates a new UIComponent instance with the given parameters
	 *
	 * @param fxmlName   the name of the FXML file
	 * @param model      the model of the UIComponent
	 * @param viewModel  the view model of the UIComponent
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
	 *
	 * @return the FXML file of the UIComponent
	 */
	public URL getFXML() {
		return fxmlFile;
	}

	/**
	 * Returns the model of the UIComponent
	 *
	 * @return the model of the UIComponent
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Returns the view model of the UIComponent
	 *
	 * @return the view model of the UIComponent
	 */
	public ViewModel getViewModel() {
		return viewModel;
	}

	/**
	 * Returns the view controller of the UIComponent
	 *
	 * @return the view controller of the UIComponent
	 */
	public ViewController getController() {
		return controller;
	}
}
