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

import java.rmi.RemoteException;
import java.util.UUID;

public interface ConnectionServer extends GenericServer {
    void open(String name, int port) throws RemoteException;
    void open(ConfigManager settings) throws RemoteException;
    boolean isRunning() throws RemoteException;

    UUID connect(ConnectionClient client) throws RemoteException;
    void disconnect(UUID sessionId) throws RemoteException;

    void registerServer(Class<? extends GenericServer> serverClass, GenericServer server) throws RemoteException;
    GenericServer getServer(Class<? extends GenericServer> serverClass) throws RemoteException;
}
