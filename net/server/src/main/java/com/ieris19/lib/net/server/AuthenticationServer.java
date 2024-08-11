package com.ieris19.lib.net.server;

import com.ieris19.lib.net.CloseableRemoteObject;
import com.ieris19.lib.utils.registry.BaseLocalRegistry;
import com.ieris19.lib.net.auth.id.SerialId;
import com.ieris19.lib.net.auth.id.SessionId;
import com.ieris19.lib.net.error.InvalidAuthenticationException;
import com.ieris19.lib.net.server.spi.AuthenticationLoader;
import com.ieris19.lib.net.auth.spi.AuthenticationValidator;
import com.ieris19.lib.net.auth.Token;
import com.ieris19.lib.utils.registry.RegistryEntry;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthenticationServer extends CloseableRemoteObject implements AuthenticationBrokerService {
    private final BaseLocalRegistry<GenericService> registry;
    private final Map<SerialId, SessionId> authenticatedSessions;
    private AuthenticationValidator validator;
    private Boolean allowMultipleSessions;

    private AuthenticationValidator getValidator() {
        if (validator == null) {
            validator = AuthenticationLoader.loadProvider().getValidator();
        }
        return validator;
    }

    public AuthenticationServer() throws RemoteException {
        super();
        registry = new BaseLocalRegistry<>();
        authenticatedSessions = new HashMap<>();
    }

    @Override
    public SessionId login(Token token, SerialId serialId) throws RemoteException {
        if (authenticatedSessions.containsKey(serialId)) {
            if (allowMultipleSessions == null) {
                allowMultipleSessions = OrchestratorServer.getInstance().getServerSettings().getBooleanProperty("server.auth/allowMultipleLogins").orElse(false);
            }
            if (!allowMultipleSessions) {
                throw new IllegalStateException("Session already authenticated");
            }
        }
        Optional<SessionId> authenticatedId = getValidator().verify(token);
        if (authenticatedId.isEmpty()) {
            throw new InvalidAuthenticationException("Invalid token");
        }
        SessionId sessionId = authenticatedId.get();
        authenticatedSessions.put(serialId, sessionId);
        return sessionId;
    }

    @Override
    public void logout(SerialId serialId, SessionId sessionId) throws RemoteException {
        if (!authenticatedSessions.containsKey(serialId)) {
            throw new IllegalStateException("Session not authenticated");
        }
        if (!authenticatedSessions.get(serialId).equals(sessionId)) {
            throw new InvalidAuthenticationException("Invalid session");
        }
        authenticatedSessions.remove(serialId);
    }

    @Override
    public <C extends GenericService> C getEntry(Class<C> entryClass) throws RemoteException {
        return registry.getEntry(entryClass);
    }

    @Override
    public void registerEntry(RegistryEntry<GenericService> entry) throws RemoteException {
        registry.registerEntry(entry.contract(), entry.implementation());
    }
}
