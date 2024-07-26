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
import com.ieris19.lib.net.server.GenericServer;

import java.io.Closeable;
import java.rmi.RemoteException;
import java.util.UUID;

public interface ConnectionClient extends GenericClient {
    void connect(String host, int port, String name) throws RemoteException;
    void connect(ConfigManager settings)  throws RemoteException;
    void disconnect()  throws RemoteException;
    boolean isConnected()  throws RemoteException;
    UUID getSessionId()  throws RemoteException;

    void registerClient(Class<? extends GenericClient> clientClass, GenericClient client)  throws RemoteException;
    GenericClient getClient(Class<? extends GenericClient> clientClass)  throws RemoteException;

    GenericServer getServer(Class<? extends GenericServer> serverClass)  throws RemoteException;
}
