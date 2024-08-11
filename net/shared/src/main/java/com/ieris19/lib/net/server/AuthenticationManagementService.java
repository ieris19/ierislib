package com.ieris19.lib.net.server;

import com.ieris19.lib.net.auth.Token;

import java.rmi.RemoteException;

public interface AuthenticationManagementService extends AuthenticationBrokerService {
    void register(Token token) throws RemoteException;
}
