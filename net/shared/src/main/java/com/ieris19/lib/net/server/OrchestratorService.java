package com.ieris19.lib.net.server;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.utils.registry.RemoteGenericRegistry;

import java.rmi.RemoteException;

public interface OrchestratorService extends GenericService, RemoteGenericRegistry<GenericService> {
    void init(ConfigManager configManager) throws RemoteException;
}
