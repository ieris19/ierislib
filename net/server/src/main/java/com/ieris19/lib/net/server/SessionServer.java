package com.ieris19.lib.net.server;

import com.ieris19.lib.net.auth.id.SerialId;
import com.ieris19.lib.net.client.OrchestratorAgent;
import com.ieris19.lib.net.client.ReporterAgent;
import com.ieris19.lib.net.error.ConnectionException;
import com.ieris19.lib.net.CloseableRemoteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Map;

public class SessionServer extends CloseableRemoteObject implements ClientBrokerService {
    private static final Logger log = LoggerFactory.getLogger(SessionServer.class);
    private final Map<SerialId, OrchestratorAgent> sessions;

    public SessionServer() throws RemoteException {
        super();
        sessions = new java.util.HashMap<>();
    }

    @Override
    public OrchestratorAgent getClient(SerialId id) {
        synchronized (sessions) {
            return sessions.get(id);
        }
    }

    @Override
    public void addClient(OrchestratorAgent client) {
        SerialId serialId;
        try {
            serialId = client.getEntry(ReporterAgent.class).getSerialId();
        } catch (RemoteException e) {
            log.error("Failed to retrieve client serialId", e);
            throw new ConnectionException("Failed to retrieve client serialId");
        }
        synchronized (sessions) {
            sessions.put(serialId, client);
            log.info("Client connected with serial id {}", serialId);
        }
    }

    @Override
    public void removeClient(SerialId id) {
        synchronized (sessions) {
            sessions.remove(id);
            log.info("Client disconnected with serial id {}", id);
        }
    }

    @Override
    public void close() throws RemoteException {
        log.info("Informing clients of server shutdown");
        Iterator<OrchestratorAgent> clients = sessions.values().iterator();
        while (clients.hasNext()) {
            try {
                clients.next().close();
            } catch (IOException e) {
                log.error("Failed to close client with ID", e);
            }
        }
        super.close();
    }
}
