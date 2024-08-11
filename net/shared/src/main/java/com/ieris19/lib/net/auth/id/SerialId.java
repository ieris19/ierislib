package com.ieris19.lib.net.auth.id;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.UUID;

public final class SerialId extends AbstractIdentifier {
    ;

    public static UUID generate() {
        try {
            MessageDigest hashGenerator = MessageDigest.getInstance("SHA-256");
            hashGenerator.update(getSystemProperties().getBytes());
            byte[] digest = hashGenerator.digest();
            return UUID.nameUUIDFromBytes(digest);
        } catch (NoSuchAlgorithmException e) {
            return UUID.randomUUID();
        }
    }

    private static String getSystemProperties() {
        Properties system = System.getProperties();
        return system.getProperty("os.name") + ":" +
                system.getProperty("os.version") + ":" +
                system.getProperty("os.arch") + ":" +
                system.getProperty("user.name") + ":" +
                system.getProperty("user.home") + ":";
    }

    public SerialId() {
        this.setId(generate());
    }
}
