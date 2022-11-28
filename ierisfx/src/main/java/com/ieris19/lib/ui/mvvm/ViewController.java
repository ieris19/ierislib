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

package com.ieris19.lib.ui.mvvm;

import com.ieris19.lib.ui.core.UIComponent;
import com.ieris19.lib.ui.core.ViewManager;
import javafx.beans.property.Property;
import javafx.scene.layout.Region;

/**
 * This class represents the view controller of a UIComponent. This class is responsible for linking the user and
 * system. It deals with user interaction, it is responsible for updating the view, and being observed by the
 * {@link ViewModel}.
 */
public abstract class ViewController {
	/**
	 * The root node of the view. All visual elements of the view are children of this node. This node is typically loaded
	 * from the fxml file.
	 */
	protected Region root;
	/**
	 * The view manager for the application that this view is part of.
	 */
	protected ViewManager viewManager;
	/**
	 * The {@link UIComponent} that this view is part of. This is used to access the {@link ViewModel} and save a
	 * reference to the relevant concrete instance of the {@link ViewModel}.
	 */
	protected UIComponent component;

	/**
	 * Because of the design of the IerisFX framework, the view controller is instantiated at startup. This means that the
	 * view controller is instantiated before the view is loaded. This method should not be extended by the user and
	 * instead, the {@link #init(ViewManager, UIComponent, Region) init()} method should be used to initialize the view.
	 */
	public ViewController() {
	}

	/**
	 * Returns the root node of the view.
	 *
	 * @return the root node of the view
	 */
	public Region getRoot() {
		return root;
	}

	/**
	 * Initializes the state of the view. As views, per our design, need to be instantiated on startup, this method delays
	 * the initialization of the view until the view is actually used. This method is called by the {@link ViewManager}
	 * when the view is first used.
	 *
	 * <p>
	 * Importantly, this method will take a generic {@link UIComponent} as a parameter, but it is expected that the
	 * concrete implementation of this method will cast the {@link ViewModel} inside to the concrete implementation
	 * corresponding to the view. This is done in order to allow a generic set of abstract classes for the the views to
	 * extend while preserving the functionality of the view.
	 * </p>
	 *
	 * @param viewManager the view manager for the application that this view is part of.
	 * @param view        the {@link UIComponent} that this view is part of.
	 * @param root        the root node of the view.
	 */
	public void init(ViewManager viewManager, UIComponent view, Region root) {
		this.root = root;
		this.viewManager = viewManager;
		this.component = view;
	}

	/**
	 * Returns the view to the initial state, regardless of the current state.
	 */
	public abstract void reset();

	/**
	 * This method should be called loading the view, after the {@link ViewModel} is set. It should call
	 * {@link ViewModel#bind(String, Property)} for every property that needs to be bound. Of course, the ViewModel
	 * needs to know how to handle these properties.
	 */
	public abstract void setBindings();
}
