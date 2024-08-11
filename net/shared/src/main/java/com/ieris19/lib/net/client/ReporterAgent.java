package com.ieris19.lib.net.client;

import com.ieris19.lib.net.auth.id.SerialId;
import com.ieris19.lib.net.auth.id.SessionId;

import java.rmi.RemoteException;
import java.util.UUID;

public interface ReporterAgent extends GenericAgent {
    SerialId getSerialId() throws RemoteException;
    SessionId getSessionId()  throws RemoteException;
    void setSessionId(SessionId sessionId)  throws RemoteException;
}
