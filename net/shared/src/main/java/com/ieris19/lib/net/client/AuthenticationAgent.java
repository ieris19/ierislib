package com.ieris19.lib.net.client;

import com.ieris19.lib.net.auth.Token;

import java.rmi.RemoteException;

public interface AuthenticationAgent extends GenericAgent {
    void setCredentials(Token token) throws RemoteException;
    Token getCredentials() throws RemoteException;
    boolean authenticate() throws RemoteException;
}
