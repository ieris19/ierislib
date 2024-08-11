package com.ieris19.lib.net.server.spi;

import com.ieris19.lib.net.auth.id.SessionId;
import com.ieris19.lib.net.auth.spi.AuthenticationValidator;
import com.ieris19.lib.net.auth.Token;

import java.util.Optional;
import java.util.UUID;

public class DefaultAuthenticationValidator implements AuthenticationValidator {
    @Override
    public Optional<SessionId> verify(Token token) {
        return Optional.of(new SessionId(UUID.randomUUID()));
    }
}
