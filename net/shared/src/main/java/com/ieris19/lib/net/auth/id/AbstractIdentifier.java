package com.ieris19.lib.net.auth.id;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.UUID;

public sealed abstract class AbstractIdentifier implements Remote, Comparable<UUID>, Serializable permits SerialId, SessionId {
    private UUID id;

    public UUID getId() {
        return id;
    }

    protected UUID setId(UUID id) {
        return this.id = id;
    }

    @Override
    public int compareTo(UUID other) {
        return id.compareTo(other);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof AbstractIdentifier abstractIdentifier)) return false;
        return id.equals(abstractIdentifier.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
