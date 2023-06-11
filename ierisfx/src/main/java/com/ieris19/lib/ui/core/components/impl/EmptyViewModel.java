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

package com.ieris19.lib.ui.core.components.impl;

import com.ieris19.lib.ui.core.components.Model;
import com.ieris19.lib.ui.core.components.ViewModel;
import javafx.beans.property.Property;

public class EmptyViewModel extends ViewModel {
	/**
	 * The model of the business logic of the view. This model should handle all operations that are not directly related
	 * to the view.
	 * <p>
	 * Because the ViewModel is empty, the model passed to this method is ignored and this method does nothing
	 * </p>
	 *
	 * @param model the reference to the model. It should be cast and stored to a backing field of the more specific model
	 *              interface used by the specific view at hand. It should NOT be stored as a concrete implementation of
	 *              the model interface.
	 */
	@Override public void setModel(Model model) {}

	/**
	 * Links a field in the view to a value in the view model. This method is used to bind a field in the view to
	 * corresponding value in the view model. The implementation of this method should be as follows:
	 * <p>
	 *   Because the view is not interactive, all properties will be ignored.
	 * </p>
	 *
	 * @param name     the name of the property being linked
	 * @param property the property being linked
	 */
	@Override public void bind(String name, Property<?> property) {}
}
