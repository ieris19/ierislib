package com.ieris19.lib.net.client;

import com.ieris19.lib.net.CloseableRemoteObject;
import com.ieris19.lib.net.auth.id.SerialId;
import com.ieris19.lib.net.auth.id.SessionId;

import java.rmi.RemoteException;
import java.util.UUID;

public class IdentityClient extends CloseableRemoteObject implements ReporterAgent {
    private SerialId serialId;
    private SessionId sessionId;

    public IdentityClient() throws RemoteException {
        super();
    }

    @Override
    public SerialId getSerialId() throws RemoteException {
        if (serialId == null) {
            serialId = new SerialId();
        }
        return serialId;
    }

    @Override
    public SessionId getSessionId() throws RemoteException {
        return sessionId;
    }

    @Override
    public void setSessionId(SessionId sessionId) throws RemoteException {
        this.sessionId = sessionId;
    }
}
