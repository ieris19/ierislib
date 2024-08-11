package com.ieris19.lib.utils.observable.remote;

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
