package com.ieris19.lib.net.server;

import com.ieris19.lib.net.auth.id.SerialId;
import com.ieris19.lib.net.client.GenericAgent;
import com.ieris19.lib.net.client.OrchestratorAgent;

import java.rmi.RemoteException;
import java.util.UUID;

public interface ClientBrokerService extends GenericService {
    GenericAgent getClient(SerialId id) throws RemoteException;
    void addClient(OrchestratorAgent client) throws RemoteException;
    void removeClient(SerialId id) throws RemoteException;
}
