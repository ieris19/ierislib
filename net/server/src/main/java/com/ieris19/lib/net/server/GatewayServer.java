package com.ieris19.lib.net.server;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.net.client.OrchestratorAgent;
import com.ieris19.lib.net.CloseableRemoteObject;
import org.slf4j.Logger;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GatewayServer extends CloseableRemoteObject implements ConnectionService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GatewayServer.class);

    private boolean isListening;

    protected GatewayServer() throws RemoteException {
        super();
        isListening = false;
        OrchestratorServer.getInstance().registerEntry(ConnectionService.class, this);
    }

    @Override
    public void startListening(String name, int port) {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind(name, this);
            setListening(true);
            log.info("Server registered to \"localhost:{}\" with the name: \"{}\"", port, name);
        } catch (AlreadyBoundException e) {
            log.error("Server already bound", e);
            throw new IllegalStateException("Server failed to register");
        } catch (RemoteException e) {
            log.error("Failed remote connection while registering the server", e);
            throw new IllegalStateException("Server failed to register");
        }
    }

    @Override
    public void startListening(ConfigManager settings) {
        int port = settings.getIntProperty("server.rmi/port").orElseThrow(() -> new IllegalStateException("Undefined property: server.rmi/port"));
        String name = settings.getProperty("server.rmi/name").orElseThrow(() -> new IllegalStateException("Undefined property: server.rmi/name"));
        this.startListening(name, port);
    }

    @Override
    public synchronized boolean isListening() {
        return isListening;
    }

    private synchronized void setListening(boolean listening) {
        isListening = listening;
    }

    @Override
    public OrchestratorServer connect(OrchestratorAgent client) throws RemoteException {
        if (!isListening()) {
            throw new IllegalStateException("Server is not running");
        }
        OrchestratorServer.getInstance().getEntry(ClientBrokerService.class).addClient(client);
        return OrchestratorServer.getInstance();
    }

//    private UUID authenticateClient(ConnectionAgent client) throws RemoteException {
//        AuthenticationBrokerService authServer = (AuthenticationBrokerService) servers.get(AuthenticationBrokerService.class);
//        AuthenticationAgent authClient = (AuthenticationAgent) client.getClient(AuthenticationAgent.class);
//        return authServer.login(authClient.getCredentials());
//    }

//    @Override
//    public void registerServer(Class<? extends GenericService> serverClass, GenericService server) {
//        if (!serverClass.isAssignableFrom(server.getClass())) {
//            throw new IllegalArgumentException("Server class does not match the server instance");
//        }
//        synchronized (servers) {
//            servers.put(serverClass, server);
//        }
//    }
}
