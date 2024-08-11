package com.ieris19.lib.utils.observable.remote;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;

/**
 * An interface identifying an object that is able to listen to properties on a remote object
 *
 * @param <Value> Type parameter of the property changing
 */
@FunctionalInterface public interface RemotePropertyChangeListener<Value extends Serializable>
		extends Remote, EventListener {
	void propertyChange(RemotePropertyChangeEvent<Value> evt) throws RemoteException;
}
