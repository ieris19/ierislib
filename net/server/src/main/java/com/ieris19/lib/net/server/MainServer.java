/*
 * Copyright 2024 Ieris19
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.ieris19.lib.net.server;

import com.ieris19.lib.files.config.ConfigManager;
import com.ieris19.lib.net.client.ConnectionClient;
import org.slf4j.Logger;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainServer extends BaseServer implements ConnectionServer {
    private static MainServer instance;

    public static synchronized MainServer getInstance() {
        if (instance == null) {
            try {
                instance = new MainServer();
            } catch (RemoteException e) {
                throw new IllegalStateException("Failed to create MainServer instance", e);
            }
        }
        return instance;
    }

    private final Map<Class<? extends GenericServer>, GenericServer> servers;
    private final Logger log = org.slf4j.LoggerFactory.getLogger(MainServer.class);
    private Map<UUID, ConnectionClient> sessions;

    private MainServer() throws RemoteException {
        super();
        servers = new HashMap<>();
        servers.put(ConnectionServer.class, this);
    }

    @Override
    public void open(String name, int port) {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind(name, this);
            sessions = new HashMap<>();
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
    public void open(ConfigManager settings) {
        int port = settings.getIntProperty("server.rmi/port").orElseThrow(() -> new IllegalStateException("Undefined property: server.rmi/port"));
        String name =  settings.getProperty("server.rmi/name").orElseThrow(() -> new IllegalStateException("Undefined property: server.rmi/name"));
        this.open(name, port);
    }

    @Override
    public synchronized boolean isRunning() {
        return sessions != null;
    }

    @Override
    public UUID connect(ConnectionClient client) {
        UUID sessionId = UUID.randomUUID();
        sessions.put(sessionId, client);
        return sessionId;
    }

    @Override
    public void disconnect(UUID sessionId) throws RemoteException {
        sessions.remove(sessionId);
    }

    @Override
    public void registerServer(Class<? extends GenericServer> serverClass, GenericServer server) {
        if (!serverClass.isAssignableFrom(server.getClass())) {
            throw new IllegalArgumentException("Server class does not match the server instance");
        }
        servers.put(serverClass, server);
    }

    @Override
    public GenericServer getServer(Class<? extends GenericServer> serverClass) {
       return servers.get(serverClass);
    }

    @Override
    public void close() throws IOException {
        sessions = null;
        servers.remove(ConnectionServer.class, this);
        servers.forEach((clazz, server) -> {
            try {
                server.close();
            } catch (IOException e) {
                log.error("Failed to close server {}", clazz.getSimpleName(), e);
            }
        });
        super.close();
        log.info("Server halted");
    }
}
