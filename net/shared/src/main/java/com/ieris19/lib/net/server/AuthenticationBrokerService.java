package com.ieris19.lib.net.server;

import com.ieris19.lib.utils.registry.RemoteGenericRegistry;
import com.ieris19.lib.net.auth.Token;
import com.ieris19.lib.net.auth.id.SerialId;
import com.ieris19.lib.net.auth.id.SessionId;

import java.rmi.RemoteException;

public interface AuthenticationBrokerService extends GenericService, RemoteGenericRegistry<GenericService> {
    SessionId login(Token token, SerialId serialId) throws RemoteException;
    void logout(SerialId serialId, SessionId sessionId) throws RemoteException;
}
