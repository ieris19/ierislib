package com.ieris19.lib.utils.registry;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteGenericRegistry<T>  extends GenericRegistry<T>, Remote {
    default void registerEntry(Class<? extends T> contract, T implementation) throws RemoteException {
        registerEntry(new RegistryEntry<>(contract, implementation));
    }
    <C extends T> C getEntry(Class<C> contract) throws RemoteException;
    void registerEntry(RegistryEntry<T> entry) throws RemoteException;
}
