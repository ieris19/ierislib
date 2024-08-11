package com.ieris19.lib.net.client;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.net.error.ConnectionError;
import com.ieris19.lib.net.server.GenericService;

import java.rmi.RemoteException;
import java.util.UUID;

public interface ConnectionAgent extends GenericAgent {
    void connect(String host, int port, String name) throws RemoteException, ConnectionError;
    void connect(ConfigManager settings) throws RemoteException, ConnectionError;
    void disconnect() throws RemoteException;
}
