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

package com.ieris19.lib.net.client;

import com.ieris19.lib.files.config.ConfigManager;
import com.ieris19.lib.net.server.ConnectionServer;
import com.ieris19.lib.net.server.GenericServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainClient extends BaseClient implements ConnectionClient {
    private static MainClient instance;
    private final Map<Class<? extends GenericClient>, GenericClient> clients;
    private final Logger log = LoggerFactory.getLogger(MainClient.class);
    private UUID sessionId;
    private ConnectionServer server;

    private MainClient() throws RemoteException {
        super();
        clients = new HashMap<>();
        clients.put(ConnectionClient.class, this);
    }

    public static synchronized MainClient getInstance() {
        if (instance == null) {
            try {
                instance = new MainClient();
            } catch (RemoteException e) {
                throw new IllegalStateException("Failed to create MainClient", e);
            }
        }
        return instance;
    }

    @Override
    public void connect(String host, int port, String name) throws RemoteException {
        try {
            log.debug("Connecting to server");
            Registry registry = LocateRegistry.getRegistry(host, port);
            server = (ConnectionServer) registry.lookup(name);
        } catch (ConnectException e) {
            log.error("Server refused to connect", e);
            throw new IllegalStateException("Server refused to connect");
        } catch (NotBoundException e) {
            log.error("No server could be found", e);
            throw new IllegalStateException("No kiosk server could be found");
        } catch (RemoteException e) {
            log.error("Failed remote connection while registering the server", e);
            throw new IllegalStateException("Failed remote connection while connecting to server");
        }
        sessionId = server.connect((ConnectionClient) this);
        log.debug("Connected to server with session id {}", sessionId);
    }

    @Override
    public void connect(ConfigManager settings) throws RemoteException {
        String host = settings.getProperty("server.rmi/host").orElseThrow(() -> new IllegalStateException("No server host found"));
        int port = settings.getIntProperty("server.rmi/port").orElseThrow(() -> new IllegalStateException("No server port found"));
        String name = settings.getProperty("server.rmi/name").orElseThrow(() -> new IllegalStateException("No server name found"));
        this.connect(host, port, name);
    }

    @Override
    public void disconnect() throws RemoteException {
        if (server != null) {
            try {
                server.disconnect(sessionId);
            } catch (RemoteException e) {
                log.error("Failed to disconnect from server", e);
            }
        }
        try {
            this.close();
        } catch (IOException e) {
            log.error("FATAL: Failed to close client", e);
            System.exit(1);
        }
    }

    @Override
    public synchronized boolean isConnected() {
        return server != null;
    }

    @Override
    public UUID getSessionId() {
        return this.sessionId;
    }

    @Override
    public void registerClient(Class<? extends GenericClient> clientClass, GenericClient client) {
        clients.put(clientClass, client);
    }

    @Override
    public GenericClient getClient(Class<? extends GenericClient> clientClass) {
        return clients.get(clientClass);
    }

    @Override
    public GenericServer getServer(Class<? extends GenericServer> serverClass) throws RemoteException {
        if (server == null) {
            throw new IllegalStateException("No server connection");
        }
        return server.getServer(serverClass);
    }

    @Override
    public void close() throws IOException {
        server = null;
        sessionId = null;
        this.clients.remove(ConnectionClient.class, this);
        clients.forEach((clazz, client) -> {
            try {
                client.close();
            } catch (IOException e) {
                log.error("Failed to close client {}", clazz.getSimpleName(), e);
            }
        });
        super.close();
        log.info("Client closed");
    }
}
