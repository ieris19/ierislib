package com.ieris19.lib.net.client;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.net.error.ConnectionException;
import com.ieris19.lib.net.server.GenericService;
import com.ieris19.lib.net.server.OrchestratorService;
import com.ieris19.lib.utils.registry.RemoteGenericRegistry;

import java.rmi.RemoteException;

public interface OrchestratorAgent extends GenericAgent, RemoteGenericRegistry<GenericAgent> {
    void init(ConfigManager manager) throws ConnectionException, RemoteException;
    boolean isConnected() throws RemoteException;
    void setRemoteOrchestrator(OrchestratorService remoteOrchestrator) throws RemoteException;
    <T extends GenericService> T getService(Class<T> serviceClass) throws RemoteException;
}
