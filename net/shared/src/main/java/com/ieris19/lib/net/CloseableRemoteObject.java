package com.ieris19.lib.net;

import com.ieris19.lib.net.server.GenericService;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;

public abstract class CloseableRemoteObject extends RemoteServer implements GenericService {
    public CloseableRemoteObject() throws RemoteException {
        NetworkHandler.expose(this);
    }

    @Override
    public void close() throws RemoteException {
        NetworkHandler.conceal(this);
    }
}
