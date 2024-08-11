package com.ieris19.lib.net.server;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.net.client.OrchestratorAgent;

import java.rmi.RemoteException;

public interface ConnectionService extends GenericService {
    void startListening(String name, int port) throws RemoteException;
    void startListening(ConfigManager settings) throws RemoteException;
    boolean isListening() throws RemoteException;

    OrchestratorService connect(OrchestratorAgent client) throws RemoteException;
}
