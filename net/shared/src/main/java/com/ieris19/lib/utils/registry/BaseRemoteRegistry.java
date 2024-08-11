package com.ieris19.lib.utils.registry;

import com.ieris19.lib.net.CloseableRemoteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

public class BaseRemoteRegistry<T> extends CloseableRemoteObject implements RemoteGenericRegistry<T> {
    private final Logger log;

    private final BaseLocalRegistry<T> entries;

    public BaseRemoteRegistry() throws RemoteException {
        this(null);
    }

    public BaseRemoteRegistry(Logger log) throws RemoteException {
        super();
        entries = new BaseLocalRegistry<>();
        this.log = log;
    }

    @Override
    public void registerEntry(Class<? extends T> contract, T implementation) throws RemoteException {
        entries.registerEntry(contract, implementation);
    }

    @Override
    public <C extends T> C getEntry(Class<C> contract) throws RemoteException {
        return entries.getEntry(contract);
    }

    @Override
    public void registerEntry(RegistryEntry<T> entry) throws RemoteException {
        if (log != null)
            log.info("Registered entry: {}", entry.contract().getSimpleName());
        entries.registerEntry(entry);
    }
}
