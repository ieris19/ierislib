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

package lib.ieris19.ui.mvvm;

import javafx.scene.layout.Region;
import lib.ieris19.ui.core.ViewManager;
import lib.ieris19.ui.core.UIComponent;

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
	 * Returns the root node of the view.
	 *
	 * @return the root node of the view
	 */
	public Region getRoot() {
		return root;
	}

	/**
	 * Sets the root node of the view.
	 *
	 * @param root the root node of the view
	 */
	public abstract void init(ViewManager viewManager, UIComponent view, Region root);

	/**
	 * Returns the view to the initial state, regardless of the current state.
	 */
	public abstract void reset();
}
