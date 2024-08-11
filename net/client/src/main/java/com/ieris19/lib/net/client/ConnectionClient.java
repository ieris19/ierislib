package com.ieris19.lib.net.client;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.net.auth.id.SerialId;
import com.ieris19.lib.net.error.ConnectionError;
import com.ieris19.lib.net.error.ConnectionException;
import com.ieris19.lib.net.server.ClientBrokerService;
import com.ieris19.lib.net.server.ConnectionService;
import com.ieris19.lib.net.server.OrchestratorService;
import com.ieris19.lib.net.CloseableRemoteObject;
import com.ieris19.lib.net.NetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConnectionClient extends CloseableRemoteObject implements ConnectionAgent {
    private final Logger log = LoggerFactory.getLogger(ConnectionAgent.class);

    public ConnectionClient() throws RemoteException {
        super();
    }

    @Override
    public void connect(String host, int port, String name) throws RemoteException, ConnectionError {
        try {
            log.debug("Connecting to server");
            Registry registry = LocateRegistry.getRegistry(host, port);
            ConnectionService gateway = (ConnectionService) registry.lookup(name);
            OrchestratorAgent orchestratorAgent = OrchestratorClient.getInstance();
            orchestratorAgent.setRemoteOrchestrator(gateway.connect(orchestratorAgent));
        } catch (ConnectException e) {
            log.error("Server refused to connect", e);
            throw new ConnectionException("Server could be reached, try again later");
        } catch (NotBoundException e) {
            log.error("No server could be found", e);
            throw new ConnectionError("Server could not be found, please contact support");
        } catch (RemoteException e) {
            log.error("Failed remote connection while registering the server", e);
            throw new ConnectionError("Connection with server failed, please try again later");
        }
        log.debug("Connected to server with serial id: {}", OrchestratorClient.getInstance().getEntry(ReporterAgent.class).getSerialId());
    }

    @Override
    public void connect(ConfigManager settings) throws RemoteException, ConnectionError {
        String host = settings.getProperty("server.rmi/host").orElseThrow(() -> new IllegalStateException("No server host found"));
        int port = settings.getIntProperty("server.rmi/port").orElseThrow(() -> new IllegalStateException("No server port found"));
        String name = settings.getProperty("server.rmi/name").orElseThrow(() -> new IllegalStateException("No server name found"));
        this.connect(host, port, name);
    }

    @Override
    public void disconnect() throws RemoteException {
        ClientBrokerService server = OrchestratorClient.getInstance().getService(ClientBrokerService.class);
        if (server != null) {
            try {
                log.info("Disconnecting from server");
                ReporterAgent reporter = OrchestratorClient.getInstance().getEntry(ReporterAgent.class);
                SerialId serialId = reporter.getSerialId();
                server.removeClient(serialId);
            } catch (RemoteException e) {
                log.error("Failed to disconnect from server", e);
            }
        }
    }

    @Override
    public void close() throws RemoteException {
        disconnect();
        super.close();
        log.info("Client closed");
    }
}
