package com.ieris19.lib.net.auth.spi;

import com.ieris19.lib.net.auth.Token;
import com.ieris19.lib.net.auth.id.SessionId;

import java.util.Optional;
import java.util.UUID;

public interface AuthenticationValidator {
    Optional<SessionId> verify(Token token);
}
