package com.ieris19.lib.net.client;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.net.NetworkHandler;
import com.ieris19.lib.net.error.ConnectionError;
import com.ieris19.lib.net.error.ConnectionException;
import com.ieris19.lib.net.server.*;
import com.ieris19.lib.utils.registry.BaseRemoteRegistry;
import com.ieris19.lib.utils.registry.RegistryEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrchestratorClient extends BaseRemoteRegistry<GenericAgent> implements OrchestratorAgent {
    private static final Logger log = LoggerFactory.getLogger(OrchestratorClient.class);
    public static OrchestratorClient instance;
    private static final List<RegistryEntry<GenericAgent>> baseAgents;

    static {
        List<RegistryEntry<GenericAgent>> agents = new ArrayList<>();
        try {
            agents.add(new RegistryEntry<>(ConnectionAgent.class, new ConnectionClient()));
            agents.add(new RegistryEntry<>(AuthenticationAgent.class, new AuthenticationClient()));
            agents.add(new RegistryEntry<>(ReporterAgent.class, new IdentityClient()));
        } catch (RemoteException e) {
            throw new IllegalStateException("Instantiation of base services failed", e);
        }
        baseAgents = Collections.unmodifiableList(agents);
    }

    public static synchronized OrchestratorClient getInstance() {
        if (instance == null) {
            try {
                instance = new OrchestratorClient();
            } catch (RemoteException e) {
                throw new IllegalStateException("Instantiation of OrchestratorClient failed", e);
            }
        }
        return instance;
    }

    private OrchestratorService server;
    private ConfigManager clientSettings;

    private OrchestratorClient() throws RemoteException {
        super(OrchestratorClient.log);
    }

    @Override
    public boolean isConnected() throws RemoteException {
        return server != null;
    }

    @Override
    public void setRemoteOrchestrator(OrchestratorService remoteOrchestrator) throws RemoteException {
        log.debug("Setting remote orchestrator");
        this.server = remoteOrchestrator;
    }

    @Override
    public <T extends GenericService> T getService(Class<T> serviceClass) throws RemoteException {
        if (clientSettings == null) {
            throw new ConnectException("Client is not connected");
        }
        if (!isConnected()) {
            try {
                getEntry(ConnectionAgent.class).connect(clientSettings);
            } catch (ConnectionException | ConnectionError e) {
                throw new ConnectionException(e.getMessage(), e);
            }
        }
        return server.getEntry(serviceClass);
    }

    @Override
    public void init(ConfigManager configManager) throws ConnectionException, RemoteException {
        log.debug("Initializing client");
        if (configManager == null) {
            throw new IllegalStateException("Server settings not set");
        }
        this.clientSettings = configManager;
        if (getEntry(OrchestratorAgent.class) == null) {
            throw new IllegalStateException("Services not registered with Orchestrator");
        }
    }

    public void registerApplicationAgents(List<RegistryEntry<GenericAgent>> applicationServices) throws RemoteException {
        try {
            registerEntry(OrchestratorAgent.class, this);
        } catch (RemoteException ignored) {
            // Calling the method locally shouldn't throw RemoteException
        }
        baseAgents.forEach((entry) -> {
            try {
                registerEntry(entry);
            } catch (RemoteException ignored) { }
        });
        applicationServices.forEach((entry) -> {
            try {
                registerEntry(entry);
            } catch (RemoteException ignored) { }
        });
    }

    @Override
    public void close() throws RemoteException {
        log.info("Connection abruptly lost with server");
        super.close();
        NetworkHandler.shutdown();
    }
}
