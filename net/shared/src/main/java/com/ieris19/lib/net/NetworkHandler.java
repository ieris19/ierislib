package com.ieris19.lib.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NetworkHandler {
    private static final Logger log = LoggerFactory.getLogger(NetworkHandler.class);
    private static final Stack<CloseableRemoteObject> exportedObjects;
    private static boolean isRunning;

    static {
        exportedObjects = new Stack<>();
        isRunning = true;
    }

    public static void expose(CloseableRemoteObject server) throws RemoteException {
        if (!isRunning) {
            isRunning = true;
        }
        synchronized (exportedObjects) {
            if (exportedObjects.contains(server)) {
                log.warn("Attempting to expose {} but it's already exported",
                        server.getClass().getSimpleName());
                return;
            }
            UnicastRemoteObject.exportObject(server, 0);
            exportedObjects.push(server);
        }
    }

    public static void conceal(CloseableRemoteObject server) {
        try {
            synchronized (exportedObjects) {
                UnicastRemoteObject.unexportObject(server, true);
                if (isRunning) {
                    exportedObjects.remove(server);
                }
            }
        } catch (NoSuchObjectException e) {
            log.warn("Failed to conceal {}: it is not exposed",
                    server.getClass().getSimpleName());
        }
    }

    public static void shutdown() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        while (!exportedObjects.isEmpty()) {
            CloseableRemoteObject exported = exportedObjects.pop();
            try {
                exported.close();
            } catch (RemoteException e) {
                log.error("Failed to close remote object", e);
                conceal(exported);
            }
        }
    }
}
