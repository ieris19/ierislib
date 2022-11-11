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

import javafx.beans.property.Property;

/**
 * This interface represents a view model. A view model is responsible for linking the view and the model. It observes
 * the view and forwards events to the model.
 */
public abstract class ViewModel {
	protected Model model;

	/**
	 * The model of the business logic of the view. This model should handle all operations that are not directly related
	 * to the view.
	 *
	 * @param model the reference to the model. It should be cast and stored to a backing field of the more specific model
	 *              interface used by the specific view at hand.
	 */
	public void setModel(Model model) {
	}

	/**
	 * Links a field in the view to a value in the view model. This method is used to bind a field in the view to
	 * corresponding value in the view model.
	 * <p>
	 * The implementation of this method should be as follows:
	 * <pre>
	 *   {@code
	 *     switch (name) {
	 *       case PropertyName -> {
	 *       // If the property needs to be updated by view model:
	 *         this.correspondingProperty.bindBidirectional((Property<CorrectPropertyType) property);
	 *       //If the property needs only to be read by view:
	 *         this.correspondingProperty.bind((Property<CorrectPropertyType) property);
	 *       }
	 *       case OtherPropertyName -> {
	 *       // ...
	 *     }
	 *   }
	 *   }
	 *   </pre>
	 * </p>
	 *
	 * @param name     the name of the property being linked
	 * @param property the property being linked
	 */
	public abstract void bind(String name, Property<?> property);
}
