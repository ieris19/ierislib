/*
 * Copyright 2024 Ieris19
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

package com.ieris19.lib.net.utils.observable.remote;

import java.io.Serializable;

/**
 * An event triggered by a property changing in a remote object
 *
 * @param <Value> Type parameter of the property changing
 */
public class RemotePropertyChangeEvent<Value extends Serializable> implements Serializable {
	/**
	 * Name of the property changed by this event
	 */
	private final String propertyName;
	/**
	 * Old value of the property changed by this event
	 */
	private final Value oldValue;
	/**
	 * Old value of the property changed by this event
	 */
	private final Value newValue;
	/**
	 * The source of the event, the object that triggered the event
	 */
	private final Serializable source;

	/**
	 * Default constructor that defines the event through a name and contains both the old and the new
	 * values
	 */
	public RemotePropertyChangeEvent(String propertyName, Value oldValue, Value newValue, Serializable source) {
		this.source = source;
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public Serializable getSource() {
		return this.source;
	}

	/**
	 * The property name defines the value that was changed by this event
	 *
	 * @return a String containing the identifier of the property
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

	/**
	 * The value that the property had before this event, sometimes null when not relevant
	 *
	 * @return the value of the property before this event
	 */
	public Value getOldValue() {
		return this.oldValue;
	}

	/**
	 * The value that the property had after this event
	 *
	 * @return the value of the property after this event
	 */
	public Value getNewValue() {
		return this.newValue;
	}
}
