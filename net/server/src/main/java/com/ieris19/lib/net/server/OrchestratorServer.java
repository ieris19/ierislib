package com.ieris19.lib.net.server;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.net.NetworkHandler;
import com.ieris19.lib.utils.registry.BaseRemoteRegistry;
import com.ieris19.lib.utils.registry.RegistryEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrchestratorServer extends BaseRemoteRegistry<GenericService> implements OrchestratorService {
    private static final Logger log = LoggerFactory.getLogger(OrchestratorServer.class);
    private static final List<RegistryEntry<GenericService>> baseServices;
    private static OrchestratorServer orchestrator;

    static {
        List<RegistryEntry<GenericService>> services = new ArrayList<>();
        try {
            services.add(new RegistryEntry<>(ConnectionService.class, new GatewayServer()));
            services.add(new RegistryEntry<>(ClientBrokerService.class, new SessionServer()));
            services.add(new RegistryEntry<>(AuthenticationBrokerService.class, new AuthenticationServer()));
        } catch (RemoteException e) {
            throw new IllegalStateException("Instantiation of base services failed", e);
        }
        baseServices = Collections.unmodifiableList(services);
    }

    private ConfigManager serverSettings;

    public ConfigManager getServerSettings() {
        return serverSettings;
    }

    private OrchestratorServer() throws RemoteException {
        super(OrchestratorServer.log);
    }

    public static synchronized OrchestratorServer getInstance() {
        if (orchestrator == null) {
            try {
                orchestrator = new OrchestratorServer();
            } catch (RemoteException e) {
                throw new IllegalStateException("Instantiation of OrchestratorServer failed", e);
            }
        }
        return orchestrator;
    }

    @Override
    public void close() throws RemoteException {
        super.close();
        log.info("Server halted");
        NetworkHandler.shutdown();
    }

    @Override
    public void init(ConfigManager configManager) throws RemoteException {
        log.info("Server initialized");
        if (configManager == null) {
            throw new IllegalStateException("Server settings not set");
        }
        this.serverSettings = configManager;
        if (getEntry(OrchestratorService.class) == null) {
            throw new IllegalStateException("Services not registered with Orchestrator");
        }
        getEntry(ConnectionService.class).startListening(serverSettings);
    }

    public void registerApplicationServices(List<RegistryEntry<GenericService>> applicationServices) throws RemoteException {
        try {
            registerEntry(OrchestratorService.class, this);
        } catch (RemoteException ignored) {
            // Calling the method locally shouldn't throw RemoteException
        }
        baseServices.forEach((entry) -> {
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

}
