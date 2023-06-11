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

import com.ieris19.lib.ui.core.components.Component;

import java.util.HashMap;

/**
 * A map of all the views in the application
 */
public class ViewMap {
	/**
	 * An empty private constructor of the ViewMap to prevent instantiation
	 */
	private ViewMap() {}

	/**
	 * An absolute map of the views included in the program running, it will be the source of truth for the
	 * {@link ViewManager View Manager}
	 */
	private static final HashMap<String, Component> components = new HashMap<>();

	/**
	 * Fetches the {@link Component Component} from the map
	 *
	 * @param id unique name identifier for the View
	 *
	 * @return the corresponding Component
	 *
	 * @throws IllegalArgumentException if the component is null
	 */
	public synchronized static Component get(String id) {
		Component component = components.get(id);
		if (component != null) {
			return component;
		} else {
			throw new IllegalArgumentException("View does not exist");
		}
	}

	/**
	 * Adds a new {@link Component Component} to the map
	 *
	 * @param id  unique name identifier for the View
	 * @param component the Component to add to the map
	 */
	public synchronized static void add(String id, Component component) {
		if (components.get(id) != null) {
			throw new IllegalArgumentException("Component ID is not unique, it already exists");
		}
		components.put(id, component);
	}
}
