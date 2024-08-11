package com.ieris19.lib.utils.observable.remote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Objects;

/**
 * This utility  that can be used by classes that support bound properties. It manages a list of
 * listeners and notifies them of {@link RemotePropertyChangeEvent RemotePropertyChangeEvents}.
 *
 * @param <Value> Type parameter of the property changing
 */
public class RemotePropertyChangeSupport<Value extends Serializable> {
	private final Logger log = LoggerFactory.getLogger(RemotePropertyChangeSupport.class);
	//private final UnicastRemoteObject sourceBean;

	/**
	 * List of all the listeners managed by this class
	 */
	private final LinkedList<RemotePropertyChangeSupport.ListenerProxy<Value>> listeners;

	/**
	 * Constructs a <code>RemotePropertyChangeSupport</code> object
	 */
	public RemotePropertyChangeSupport() {
		//this.sourceBean = sourceBean;
		this.listeners = new LinkedList();
	}

	/**
	 * Adds a listener to this object, whenever a property change event is fired, all listeners will
	 * be notified
	 *
	 * @param listener Object listening to this class
	 */
	public synchronized void addPropertyChangeListener(RemotePropertyChangeListener<Value> listener) {
		this.listeners.add(new RemotePropertyChangeSupport.ListenerProxy(listener));
	}

	/**
	 * Removes a listener to this object, so that whenever a property change event is fired, it will
	 * not be notified anymore
	 *
	 * @param listener Object listening to this class
	 */
	public synchronized void removePropertyChangeListener(
			RemotePropertyChangeListener<Value> listener) {
		this.listeners.remove(new RemotePropertyChangeSupport.ListenerProxy(listener));
	}

	/**
	 * Notifies all listeners that there has been a property change by sending a
	 * {@link RemotePropertyChangeEvent} to every listener
	 *
	 * @param evt the event object that contains the information about the change
	 *
	 * @throws RemoteException If a problem arises during RMI communication
	 */
	public void firePropertyChange(RemotePropertyChangeEvent<Value> evt) throws RemoteException {
		if (!Objects.equals(evt.getOldValue(), evt.getNewValue())) {
			synchronized (this) {

                for (ListenerProxy<Value> listener : this.listeners) {
                    ListenerProxy<Value> proxy = (ListenerProxy) listener;
                    proxy.propertyChange(evt);
                }
				log.trace(
						"Property change event in property {} to value {}", evt.getPropertyName(), evt.getNewValue());
			}
		}
	}

	/**
	 * Constructs a {@link RemotePropertyChangeEvent} and subsequently notifies all listeners by
	 * calling the overloaded
	 * {@link RemotePropertyChangeSupport#firePropertyChange(RemotePropertyChangeEvent)
	 * firePropertyChange(Event)} method
	 *
	 * @param propertyName name of the property changing
	 * @param oldValue     the previous value of the property
	 * @param newValue     the new value of the property
	 *
	 * @throws RemoteException If a problem arises during RMI communication
	 */
	public void firePropertyChange(String propertyName, Value oldValue, Value newValue, Serializable source)
			throws RemoteException {
		this.firePropertyChange(new RemotePropertyChangeEvent<>(propertyName, oldValue, newValue, source));
	}

	/**
	 * Constructs a {@link RemotePropertyChangeEvent} and subsequently notifies all listeners by
	 * calling the overloaded
	 * {@link RemotePropertyChangeSupport#firePropertyChange(RemotePropertyChangeEvent)
	 * firePropertyChange(Event)} method. In this method call, the old value of the property is
	 * irrelevant and is automatically assigned null
	 *
	 * @param propertyName name of the property changing
	 * @param newValue     the new value of the property
	 *
	 * @throws RemoteException If a problem arises during RMI communication
	 */
	public void firePropertyChange(String propertyName, Value newValue, Serializable source) throws RemoteException {
		this.firePropertyChange(propertyName, null, newValue, source);
	}

	private static class ListenerProxy<Value extends Serializable> {
		private final String propertyName;
		private final RemotePropertyChangeListener<Value> listener;

		public ListenerProxy(String propertyName, RemotePropertyChangeListener<Value> listener) {
			this.propertyName = propertyName;
			this.listener = listener;
		}

		public ListenerProxy(RemotePropertyChangeListener<Value> listener) {
			this((String) null, listener);
		}

		public boolean acceptsProperty(String propertyName) {
			return this.propertyName == null || this.propertyName.equals(propertyName);
		}

		public void propertyChange(RemotePropertyChangeEvent<Value> evt) throws RemoteException {
			if (this.acceptsProperty(evt.getPropertyName())) {
				this.listener.propertyChange(evt);
			}
		}

		public RemotePropertyChangeListener<Value> getListener() {
			return this.listener;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			} else if (o != null && this.getClass() == o.getClass()) {
				RemotePropertyChangeSupport.ListenerProxy<?> that = (RemotePropertyChangeSupport.ListenerProxy) o;
				return Objects.equals(this.propertyName, that.propertyName) && this.listener.equals(
						that.listener);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return Objects.hash(this.propertyName, this.listener);
		}
	}
}
