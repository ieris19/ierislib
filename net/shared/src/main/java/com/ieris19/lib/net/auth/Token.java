package com.ieris19.lib.net.auth;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.Arrays;
import java.util.Map;

public record Token(Map<String, Object> credentials) implements Remote, Serializable {
    public Token (String[]... credentials) {
        this(Map.ofEntries(Arrays.stream(credentials).map(c -> Map.entry(c[0], c[1])).toArray(Map.Entry[]::new)));
    }

    public Object getCredentials(String key) {
        return credentials.get(key);
    }
}
