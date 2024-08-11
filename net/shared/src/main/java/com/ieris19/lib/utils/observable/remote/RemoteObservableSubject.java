package com.ieris19.lib.utils.observable.remote;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObservableSubject<T extends Serializable> extends Remote {
	void addListener(RemotePropertyChangeListener<T> listener) throws RemoteException;
	void removeListener(RemotePropertyChangeListener<T> listener) throws RemoteException;
}
