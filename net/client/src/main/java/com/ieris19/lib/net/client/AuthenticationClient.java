package com.ieris19.lib.net.client;

import com.ieris19.lib.net.CloseableRemoteObject;
import com.ieris19.lib.net.auth.Token;
import com.ieris19.lib.net.auth.id.SerialId;
import com.ieris19.lib.net.auth.id.SessionId;
import com.ieris19.lib.net.error.InvalidAuthenticationException;
import com.ieris19.lib.net.server.AuthenticationBrokerService;

import java.rmi.RemoteException;

public class AuthenticationClient extends CloseableRemoteObject implements AuthenticationAgent {
    private Token token;

    public AuthenticationClient() throws RemoteException {
        super();
        token = null;
    }

    @Override
    public synchronized void setCredentials(Token token) throws RemoteException {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        this.token = token;
    }

    @Override
    public synchronized Token getCredentials() throws RemoteException {
        return token;
    }

    @Override
    public boolean authenticate() throws RemoteException {
        if (token == null) {
            throw new IllegalStateException("Token not set");
        }
        try {
            OrchestratorAgent mainAgent = OrchestratorClient.getInstance();
            SerialId fingerprint = mainAgent.getEntry(ReporterAgent.class).getSerialId();
            SessionId authedSession = mainAgent.getService(AuthenticationBrokerService.class)
                    .login(token, fingerprint);
            mainAgent.getEntry(ReporterAgent.class).setSessionId(authedSession);
            return true;
        } catch (InvalidAuthenticationException e) {
            return false;
        }
    }
}
