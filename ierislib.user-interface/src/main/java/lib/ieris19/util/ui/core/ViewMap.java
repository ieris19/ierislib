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

package lib.ieris19.util.ui.core;

import java.util.HashMap;

/**
 * A map of all the views in the application
 */
public class ViewMap {
	/**
	 * An empty private constructor of the ViewMap to prevent instantiation
	 */
	private ViewMap() {
	}

	/**
	 * An absolute map of the views included in the program running, it will be the source of truth for the
	 * {@link ViewManager ViewManager}
	 */
	private static final HashMap<String, UIComponent> views = new HashMap<>();

	/**
	 * Fetches the {@link lib.ieris19.util.ui.core.UIComponent UI Component} from the map
	 *
	 * @param key unique name identifier for the View
	 *
	 * @return the corresponding UI Component
	 *
	 * @throws IllegalArgumentException if the UI component is null
	 */
	public synchronized static UIComponent get(String key) {
		UIComponent view = views.get(key);
		if (view != null) {
			return view;
		} else {
			throw new IllegalArgumentException("View does not exist");
		}
	}

	/**
	 * Adds a new {@link lib.ieris19.util.ui.core.UIComponent UI Component} to the map
	 *
	 * @param key  unique name identifier for the View
	 * @param view the UI Component to add to the map
	 */
	public synchronized static void add(String key, UIComponent view) {
		if (views.get(key) != null) {
			throw new IllegalArgumentException("View ID is not unique, it already exists");
		}
		views.put(key, view);
	}
}
